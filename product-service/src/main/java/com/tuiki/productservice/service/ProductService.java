package com.tuiki.productservice.service;

import com.tuiki.productservice.domain.Product;
import com.tuiki.productservice.exception.ProductNotFoundException;
import com.tuiki.productservice.exception.ProductValidationException;
import com.tuiki.productservice.repository.CategoryRepository;
import com.tuiki.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product updateProduct(Integer id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        validateProduct(product);

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    public void validateProduct(Product product) {
        if (ObjectUtils.isEmpty(product.getName())) {
            throw new ProductValidationException("El nombre no debe estar vacío");
        }

        if (ObjectUtils.isEmpty(product.getDescription())) {
            throw new ProductValidationException("La descripción no debe estar vacía");
        }

        if (ObjectUtils.isEmpty(product.getPrice())) {
            throw new ProductValidationException("El precio no debe estar vacío");
        }

        if (ObjectUtils.isEmpty(product.getQuantity())) {
            throw new ProductValidationException("La cantidad no debe estar vacía");
        }

        if (ObjectUtils.isEmpty(product.getCategory())) {
            throw new ProductValidationException("La categoría no debe estar vacía");
        }

        if (product.getPrice() <= 0) {
            throw new ProductValidationException("El precio debe ser un número positivo");
        }

        if (product.getQuantity() < 0) {
            throw new ProductValidationException("La cantidad debe ser un número positivo o cero");
        }

        if (!categoryRepository.existsById(product.getCategory().getId())) {
            throw new ProductValidationException("La categoría no existe");
        }
    }
}
