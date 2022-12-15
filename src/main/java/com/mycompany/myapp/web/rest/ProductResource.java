package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.group.ProductType;
import com.mycompany.myapp.repository.ProductRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.ProductService;
import com.mycompany.myapp.service.dto.ProductDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.errors.NameAlreadyUsedException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("api/admin")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductService productService;

    private final ProductRepository productRepository;

    public ProductResource(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @PostMapping("/products")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<ResponseEntity<Product>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.debug("REST request to save Product: {}", productDTO.getName());

        if (productDTO.getId() != null) throw new BadRequestAlertException(
            "A new product cannot already have an ID",
            "productManagement",
            "idexists"
        );

        return productRepository
            .findOneByName(productDTO.getName().toLowerCase())
            .hasElement()
            .flatMap(nameExists -> {
                if (Boolean.TRUE.equals(nameExists)) {
                    return Mono.error(new NameAlreadyUsedException());
                }
                return productService.createProduct(productDTO);
            })
            .map(product -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/admin/products/" + product.getName()))
                        .headers(HeaderUtil.createAlert(applicationName, "productManagement.created", product.getName()))
                        .body(product);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @PutMapping("/products")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<ResponseEntity<ProductDTO>> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.debug("REST request to update Product: {}", productDTO.getName());
        return productRepository
            .findOneByName(productDTO.getName())
            .filter(product -> !product.getId().equals(productDTO.getId()))
            .hasElement()
            .flatMap(nameExists -> {
                if (Boolean.TRUE.equals(nameExists)) {
                    return Mono.error(new NameAlreadyUsedException());
                }
                return productService.updateProduct(productDTO);
            })
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(product ->
                ResponseEntity
                    .ok()
                    .headers(HeaderUtil.createAlert(applicationName, "productManagement.updated", productDTO.getName()))
                    .body(product)
            );
    }

    //    @GetMapping("/products")
    //    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    //    public Mono<ResponseEntity<Flux<ProductDTO>>> getProducts(@ParameterObject ServerHttpRequest request, @ParameterObject Pageable pageable) {
    //        log.debug("REST request to get all Products");
    //        return productService
    //            .countProduct()
    //            .map(total -> new PageImpl<>(new ArrayList<>(), pageable, total))
    //            .map(page -> PaginationUtil.generatePaginationHttpHeaders(UriComponentsBuilder.fromHttpRequest(request), page))
    //            .map(headers -> ResponseEntity.ok().headers(headers).body(productService.getAllProducts(pageable)));
    //    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<ResponseEntity<Flux<ProductDTO>>> getProductsByType(@ParameterObject Pageable pageable, @RequestParam ProductType type) {
        log.debug("REST request to get all Products with {} type", type);
        return Mono.just(ResponseEntity.ok().body(productService.getAllProductsWithType(pageable, type)));
    }

    @GetMapping("/products/{name}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<ProductDTO> getProduct(@PathVariable("name") String name) {
        log.debug("REST request to get Product: {}", name);
        return productService
            .getProductByName(name)
            .map(ProductDTO::new)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/products/{name}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable("name") String name) {
        log.debug("REST request to delete Product: {}", name);
        return productService
            .deleteProduct(name.toLowerCase())
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "productManagement.deleted", name)).build()
                )
            );
    }
}
