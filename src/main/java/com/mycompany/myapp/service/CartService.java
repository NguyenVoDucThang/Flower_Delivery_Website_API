package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.domain.Cart_Product;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.group.CartStatus;
import com.mycompany.myapp.repository.*;
import com.mycompany.myapp.service.dto.CartDTO;
import com.mycompany.myapp.service.dto.ProductCartDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CartProductRepository cartProductRepository;

    private final DeliveryRepository deliveryRepository;

    private final ReceiverRepository receiverRepository;

    public CartService(
        CartRepository cartRepository,
        UserRepository userRepository,
        ProductRepository productRepository,
        CartProductRepository cartProductRepository,
        DeliveryRepository deliveryRepository,
        ReceiverRepository receiverRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
        this.deliveryRepository = deliveryRepository;
        this.receiverRepository = receiverRepository;
    }

    @Transactional
    public Mono<Cart> createCart(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setStatus(cartDTO.getStatus());
        userRepository
            .findById(cartDTO.getUser().getId())
            .map(user -> {
                cart.setUser(user);
                return user;
            });
        deliveryRepository
            .findById(cartDTO.getDelivery().getId())
            .map(delivery -> {
                cart.setDelivery(delivery);
                return delivery;
            });
        receiverRepository
            .findById(cartDTO.getReceiver().getId())
            .map(receiver -> {
                cart.setReceiver(receiver);
                return receiver;
            });
        Mono<Cart> newCart = cartRepository.save(cart).flatMap(savedCart -> Mono.just(savedCart));
        int total = 0;
        for (ProductCartDTO productDTO : cartDTO.getProducts()) {
            productRepository
                .findById(productDTO.getId())
                .map(product1 -> {
                    cartProductRepository.save(new Cart_Product(cart, product1, productDTO.getQuantity()));
                    productDTO.setTotal(product1.getPrice() * productDTO.getQuantity());
                    return product1;
                });
            total += productDTO.getTotal();
        }
        cart.setTotal(total);
        log.debug("Created Information for Cart: {}", cart);
        return newCart;
    }

    public Mono<Long> countCartWithStatus(CartStatus cartStatus) {
        return cartRepository.countByStatus(cartStatus);
    }

    @Transactional(readOnly = true)
    public Flux<CartDTO> getAllCartsByStatus(Pageable pageable, CartStatus cartStatus) {
        return cartRepository.findAllByStatus(pageable, cartStatus).map(CartDTO::new);
    }

    @Transactional
    public Mono<Void> deleteCart(String id) {
        return cartRepository
            .findOneById(id)
            .flatMap(cart -> cartRepository.delete(cart).thenReturn(cart))
            .doOnNext(cart -> log.debug("Deleted Cart: {}", cart))
            .then();
    }
}
