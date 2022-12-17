package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.domain.composite_key.CartProductKey;
import com.mycompany.myapp.domain.group.CartStatus;
import com.mycompany.myapp.repository.*;
import com.mycompany.myapp.service.dto.CartDTO;
import com.mycompany.myapp.service.dto.ProductCartDTO;
import com.mycompany.myapp.web.rest.errors.notexists.DeliveryNotExistsException;
import com.mycompany.myapp.web.rest.errors.notexists.ReceiverNotExistsException;
import com.mycompany.myapp.web.rest.errors.notexists.UserNotExistsException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final DeliveryRepository deliveryRepository;

    private final ReceiverRepository receiverRepository;

    public CartService(
        CartRepository cartRepository,
        UserRepository userRepository,
        ProductRepository productRepository,
        DeliveryRepository deliveryRepository,
        ReceiverRepository receiverRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.deliveryRepository = deliveryRepository;
        this.receiverRepository = receiverRepository;
    }

    @Transactional
    public CartDTO createCart(CartDTO cartDTO) {
        Cart cart = new Cart();

        // set status of cart
        cart.setStatus(cartDTO.getStatus());
        // set user
        Optional<User> userOptional = userRepository.findById(cartDTO.getUser().getId());
        if (!userOptional.isPresent()) throw new UserNotExistsException(); else cart.setUser(userOptional.get());
        // set delivery information
        if (cartDTO.getDelivery() != null) {
            Optional<Delivery> deliveryOptional = deliveryRepository.findById(cartDTO.getDelivery().getId());
            if (!deliveryOptional.isPresent()) throw new DeliveryNotExistsException(); else cart.setDelivery(deliveryOptional.get());
        }
        // set receiver information
        if (cartDTO.getReceiver() != null) {
            Optional<Receiver> receiverOptional = receiverRepository.findById(cartDTO.getReceiver().getId());
            if (!receiverOptional.isPresent()) throw new ReceiverNotExistsException(); else cart.setReceiver(receiverOptional.get());
        }

        cartRepository.save(cart);

        // set total and list of product
        int total = 0;
        Set<Cart_Product> cartProductSet = new HashSet<>();
        for (ProductCartDTO productDTO : cartDTO.getProducts()) {
            productRepository
                .findById(productDTO.getId())
                .map(product1 -> {
                    CartProductKey key = new CartProductKey(cart.getId(), product1.getId());
                    Cart_Product cp = new Cart_Product(key, cart, product1, productDTO.getQuantity());
                    cartProductSet.add(cp);
                    productDTO.setTotal(cp.getTotal());
                    return product1;
                });
            total += productDTO.getTotal();
        }
        cart.setCartProductSet(cartProductSet);
        cart.setTotal(total);

        log.debug("Created Information for Cart: {}", cart);
        return new CartDTO(cart);
    }

    @Transactional(readOnly = true)
    public Page<CartDTO> getAllCartsByStatus(Pageable pageable, CartStatus cartStatus) {
        return cartRepository.findAllByStatus(pageable, cartStatus).map(CartDTO::new);
    }

    @Transactional
    public void deleteCart(String id) {
        cartRepository
            .findOneById(id)
            .ifPresent(cart -> {
                cartRepository.delete(cart);
                log.debug("Deleted Cart: {}", cart);
            });
    }
}
