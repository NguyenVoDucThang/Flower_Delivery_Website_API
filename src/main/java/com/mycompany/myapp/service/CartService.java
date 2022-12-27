package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.domain.composite_key.CartProductKey;
import com.mycompany.myapp.domain.group.CartStatus;
import com.mycompany.myapp.repository.*;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.CartDTO;
import com.mycompany.myapp.service.dto.ProductCartDTO;
import com.mycompany.myapp.web.rest.errors.notexists.DeliveryNotExistsException;
import com.mycompany.myapp.web.rest.errors.notexists.ReceiverNotExistsException;
import com.mycompany.myapp.web.rest.errors.notexists.UserNotExistsException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    private final CartProductRepository cartProductRepository;

    public CartService(
        CartRepository cartRepository,
        UserRepository userRepository,
        ProductRepository productRepository,
        DeliveryRepository deliveryRepository,
        ReceiverRepository receiverRepository,
        CartProductRepository cartProductRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.deliveryRepository = deliveryRepository;
        this.receiverRepository = receiverRepository;
        this.cartProductRepository = cartProductRepository;
    }

    @Transactional
    public CartDTO createCart(CartDTO cartDTO) {
        Cart cart = new Cart();

        // set status of cart
        cart.setStatus(CartStatus.Delivering);
        // set user
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                cart.setUser(user);
                cart.setCreatedBy(user.getLogin());
                cart.setLastModifiedBy(user.getLogin());
            });
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

    @Transactional
    public Optional<CartDTO> updateCart(CartDTO cartDTO) {
        return Optional
            .of(cartRepository.findById(cartDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(cart -> {
                cart.setStatus(cartDTO.getStatus());
                if (cartDTO.getReceiver() != null) cart.setReceiver(cartDTO.getReceiver());
                if (cartDTO.getDelivery() != null) cart.setDelivery(cartDTO.getDelivery());
                SecurityUtils
                    .getCurrentUserLogin()
                    .flatMap(userRepository::findOneByLogin)
                    .ifPresent(user -> {
                        cart.setUser(user);
                        cart.setLastModifiedBy(user.getLogin());
                        cart.setLastModifiedDate(Instant.now());
                    });
                if (!cartDTO.getProducts().isEmpty()) {
                    Set<Cart_Product> cartProductSet = cart.getCartProductSet();
                    int total = cart.getTotal();
                    for (ProductCartDTO productDTO : cartDTO.getProducts()) {
                        Product product = productRepository.findById(productDTO.getId()).get();

                        CartProductKey key = new CartProductKey(cart.getId(), product.getId());
                        Optional<Cart_Product> cart_product = cartProductRepository.findById(key);
                        Cart_Product cp;
                        if (cart_product.isPresent()) {
                            cp = cart_product.get();
                            total -= cp.getTotal();
                            cp.setQuantity(productDTO.getQuantity());
                        } else {
                            cp = new Cart_Product(key, cart, product, productDTO.getQuantity());
                            cartProductSet.add(cp);
                        }
                        total += cp.getTotal();
                    }
                    cart.setTotal(total);
                }
                log.debug("Changed Information for Cart: {}", cart);
                return cart;
            })
            .map(CartDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<CartDTO> getAllCartsByStatus(Pageable pageable, CartStatus cartStatus) {
        User user = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).get();
        Set<String> auths = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
        Page<Cart> carts;
        if (auths.contains(AuthoritiesConstants.ADMIN)) {
            if (cartStatus == null) carts = cartRepository.findAll(pageable); else carts =
                cartRepository.findAllByStatus(pageable, cartStatus);
        } else {
            if (cartStatus == null) carts = cartRepository.findAllByUser(pageable, user); else carts =
                cartRepository.findAllByStatusAndUser(pageable, cartStatus, user);
        }
        return carts.map(CartDTO::new);
    }

    @Transactional
    public void deleteCart(String id) {
        Cart cart = cartRepository.findOneById(id).get();
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                Set<String> auths = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
                if (auths.contains(AuthoritiesConstants.ADMIN)) {
                    cartRepository.delete(cart);
                    log.debug("Deleted Cart: {}", cart);
                } else if (cart.getUser().equals(user)) {
                    cartRepository.delete(cart);
                    log.debug("Deleted Cart: {}", cart);
                }
            });
    }
}
