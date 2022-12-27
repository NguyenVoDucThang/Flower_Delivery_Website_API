package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.group.ProductType;
import com.mycompany.myapp.repository.ProductRepository;
import com.mycompany.myapp.service.ProductService;
import com.mycompany.myapp.service.dto.ProductDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class PublicResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductService productService;

    public PublicResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProductsByType(
        @ParameterObject Pageable pageable,
        @RequestParam(required = false) ProductType type
    ) {
        log.debug("REST request to get all Products with {} type", type);

        final Page<ProductDTO> page = productService.getAllProductsWithType(pageable, type);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") String id) {
        log.debug("REST request to get Product: {}", id);
        return ResponseUtil.wrapOrNotFound(productService.getProductByID(id));
    }
}
