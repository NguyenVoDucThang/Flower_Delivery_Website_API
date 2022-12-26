package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.group.ProductType;
import com.mycompany.myapp.repository.ProductRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.dto.ProductDTO;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName().toLowerCase());
        product.setType(productDTO.getType());
        product.setPrice(productDTO.getPrice());
        product.setFeature(productDTO.getFeature());
        product.setDetail(productDTO.getDetail());
        product.setImage_url(productDTO.getImage_url());
        product.setSold_num(productDTO.getSold_num());
        product.setQuantity(productDTO.getQuantity());

        productRepository.save(product);
        log.debug("Created Information for Product: {}", product);
        return product;
    }

    @Transactional
    public Optional<ProductDTO> updateProduct(ProductDTO productDTO) {
        return Optional
            .of(productRepository.findById(productDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(product -> {
                product.setName(productDTO.getName().toLowerCase());
                product.setType(productDTO.getType());
                product.setPrice(productDTO.getPrice());
                product.setFeature(productDTO.getFeature());
                product.setDetail(productDTO.getDetail());
                product.setImage_url(productDTO.getImage_url());
                product.setSold_num(productDTO.getSold_num());
                product.setQuantity(productDTO.getQuantity());
                log.debug("Changed Information for Product: {}", product);
                return product;
            })
            .map(ProductDTO::new);
    }

    //    public Page<ProductDTO> getAllProducts(Pageable pageable) {
    //        return productRepository.findAll().map(ProductDTO::new);
    //    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProductsWithType(Pageable pageable, ProductType type) {
        return productRepository.findAllByType(pageable, type).map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<ProductDTO> getProductByID(String id) {
        return productRepository.findById(id).map(ProductDTO::new);
    }

    @Transactional
    public void deleteProduct(String id) {
        productRepository
            .findById(id)
            .ifPresent(product -> {
                productRepository.delete(product);
                log.debug("Deleted Product: {}", product);
            });
    }
}
