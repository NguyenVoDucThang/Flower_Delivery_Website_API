package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.group.ProductType;
import com.mycompany.myapp.repository.ProductRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Mono<Product> createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName().toLowerCase());
        product.setType(productDTO.getType());
        product.setPrice(productDTO.getPrice());
        product.setFeature(productDTO.getFeature());
        product.setDetail(productDTO.getDetail());
        product.setImage_url(productDTO.getImage_url());
        product.setSold_num(productDTO.getSold_num());
        product.setQuantity(productDTO.getQuantity());
        System.out.println("Product ID:" + product.getId());

        log.debug("Created Information for Product: {}", product);
        return saveProduct(product);
    }

    @Transactional
    public Mono<ProductDTO> updateProduct(ProductDTO productDTO) {
        return productRepository
            .findById(productDTO.getId())
            .flatMap(product -> {
                product.setName(productDTO.getName().toLowerCase());
                product.setType(productDTO.getType());
                product.setPrice(productDTO.getPrice());
                product.setFeature(productDTO.getFeature());
                product.setDetail(productDTO.getDetail());
                product.setImage_url(productDTO.getImage_url());
                product.setSold_num(productDTO.getSold_num());
                product.setQuantity(productDTO.getQuantity());
                return Mono.just(product);
            })
            .flatMap(this::saveProduct)
            .doOnNext(product -> log.debug("Changed Information for Product: {}", product))
            .map(ProductDTO::new);
    }

    @Transactional
    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product).flatMap(savedProduct -> Mono.just(savedProduct));
    }

    //    @Transactional(readOnly = true)
    //    public Mono<Long> countProduct() {
    //        return productRepository.count();
    //    }

    //    public Flux<ProductDTO> getAllProducts(Pageable pageable) {
    //        return productRepository.findAll().map(ProductDTO::new);
    //    }

    @Transactional(readOnly = true)
    public Mono<Long> countProductWithType(ProductType type) {
        return productRepository.countByType(type);
    }

    @Transactional(readOnly = true)
    public Flux<ProductDTO> getAllProductsWithType(Pageable pageable, ProductType type) {
        return productRepository.findAllByType(pageable, type).map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public Mono<Product> getProductByName(String name) {
        return productRepository.findOneByName(name);
    }

    @Transactional
    public Mono<Void> deleteProduct(String name) {
        return productRepository
            .findOneByName(name)
            .flatMap(product -> productRepository.delete(product).thenReturn(product))
            .doOnNext(product -> log.debug("Deleted Product: {}", product))
            .then();
    }
}
