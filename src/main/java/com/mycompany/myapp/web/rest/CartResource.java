package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.domain.group.CartStatus;
import com.mycompany.myapp.repository.CartRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.CartService;
import com.mycompany.myapp.service.dto.CartDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import io.jsonwebtoken.Header;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/admin")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartService cartService;

    private final CartRepository cartRepository;

    public CartResource(CartService cartService, CartRepository cartRepository) {
        this.cartService = cartService;
        this.cartRepository = cartRepository;
    }

    @PostMapping("/carts")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<ResponseEntity<Cart>> createCart(@Valid @RequestBody CartDTO cartDTO) {
        log.debug("REST request to save Cart: {}", cartDTO);

        if (cartDTO.getId() != null) throw new BadRequestAlertException(
            "A new cart cannot already have an ID",
            "cartManagement",
            "idexists"
        );

        Mono<Cart> newCart = cartService.createCart(cartDTO);
        return newCart.map(cart -> {
            try {
                return ResponseEntity
                    .created(new URI("/api/admin/carts/" + cart.getId()))
                    .headers(HeaderUtil.createAlert(applicationName, "cartManagement.created", cart.getId()))
                    .body(cart);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping("/carts")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<ResponseEntity<Flux<CartDTO>>> getCartsByStatus(@ParameterObject Pageable pageable, @RequestParam CartStatus cartStatus) {
        log.debug("REST request to get all Carts with {} status", cartStatus);
        return Mono.just(ResponseEntity.ok().body(cartService.getAllCartsByStatus(pageable, cartStatus)));
    }

    @DeleteMapping("/carts/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<ResponseEntity<Void>> deleteCart(@PathVariable("id") String id) {
        log.debug("REST request to delete Cart: {}", id);

        return cartService
            .deleteCart(id)
            .then(
                Mono.just(ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "cartManagement.deleted", id)).build())
            );
    }
}
