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
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product: {}", productDTO.getName());

        if (productDTO.getId() != null) {
            throw new BadRequestAlertException("A new product cannot already have an ID", "productManagement", "idexists");
        } else if (productRepository.findOneByName(productDTO.getName().toLowerCase()).isPresent()) {
            throw new NameAlreadyUsedException();
        } else {
            Product product = productService.createProduct(productDTO);
            return ResponseEntity
                .created(new URI("/api/admin/products/" + product.getName()))
                .headers(HeaderUtil.createAlert(applicationName, "productManagement.created", product.getName()))
                .body(product);
        }
    }

    @PutMapping("/products")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.debug("REST request to update Product: {}", productDTO.getName());
        Optional<Product> existingProduct = productRepository.findOneByName(productDTO.getName());
        if (existingProduct.isPresent() && existingProduct.get().getId().equals(productDTO.getId())) throw new NameAlreadyUsedException();

        Optional<ProductDTO> updatedProduct = productService.updateProduct(productDTO);
        return ResponseUtil.wrapOrNotFound(
            updatedProduct,
            HeaderUtil.createAlert(applicationName, "A product is updated with identifier " + productDTO.getId(), productDTO.getId())
        );
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProductsByType(@ParameterObject Pageable pageable, @RequestParam ProductType type) {
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

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        log.debug("REST request to delete Product: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createAlert(applicationName, "A product is deleted with identifier " + id, id))
            .build();
    }
}
