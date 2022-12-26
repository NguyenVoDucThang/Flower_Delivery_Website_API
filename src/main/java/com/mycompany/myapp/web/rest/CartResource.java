package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.domain.group.CartStatus;
import com.mycompany.myapp.repository.CartRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.CartService;
import com.mycompany.myapp.service.dto.CartDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

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
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<CartDTO> createCart(@Valid @RequestBody CartDTO cartDTO) throws URISyntaxException {
        log.debug("REST request to save Cart: {}", cartDTO);

        if (cartDTO.getId() != null) throw new BadRequestAlertException(
            "A new cart cannot already have an ID",
            "cartManagement",
            "idexists"
        );

        CartDTO newCart = cartService.createCart(cartDTO);

        return ResponseEntity
            .created(new URI("/api/admin/carts/" + newCart.getId()))
            .headers(HeaderUtil.createAlert(applicationName, "cartManagement.created", newCart.getId()))
            .body(newCart);
    }

    @GetMapping("/carts")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<CartDTO>> getCartsByStatus(@ParameterObject Pageable pageable, @RequestParam CartStatus cartStatus) {
        log.debug("REST request to get all Carts with {} status", cartStatus);

        final Page<CartDTO> page = cartService.getAllCartsByStatus(pageable, cartStatus);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PutMapping("/carts")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CartDTO> updateCart(@Valid @RequestBody CartDTO cartDTO) {
        log.debug("REST request to update Cart: {}", cartDTO.getId());
        Optional<CartDTO> updatedCart = cartService.updateCart(cartDTO);
        return ResponseUtil.wrapOrNotFound(
            updatedCart,
            HeaderUtil.createAlert(applicationName, "A cart is updated with identifier: " + cartDTO.getId(), cartDTO.getId())
        );
    }

    @DeleteMapping("/carts/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteCart(@PathVariable("id") String id) {
        log.debug("REST request to delete Cart: {}", id);
        cartService.deleteCart(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createAlert(applicationName, "A cart is deleted with identifier " + id, id))
            .build();
    }
}
