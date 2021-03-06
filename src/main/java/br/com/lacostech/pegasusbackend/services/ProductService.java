package br.com.lacostech.pegasusbackend.services;

import br.com.lacostech.pegasusbackend.model.entities.Category;
import br.com.lacostech.pegasusbackend.model.entities.Product;
import br.com.lacostech.pegasusbackend.model.requests.ProductRequest;
import br.com.lacostech.pegasusbackend.model.responses.ProductDetailedResponse;
import br.com.lacostech.pegasusbackend.model.responses.ProductMinResponse;
import br.com.lacostech.pegasusbackend.repositories.CategoryRepository;
import br.com.lacostech.pegasusbackend.repositories.ProductRepository;
import br.com.lacostech.pegasusbackend.services.exceptions.DatabaseException;
import br.com.lacostech.pegasusbackend.services.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductMinResponse> findAll(
            final Long categoryId,
            final String productName,
            final Pageable pageable) {
        Category category = categoryId == 0 ? null : categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category id " + categoryId + " does not exists"));
        List<Category> categories = Objects.isNull(category) ? null : List.of(category);

        Page<Product> products = productRepository.find(categories, productName, pageable);
        productRepository.findProductsWithCategories(products.getContent());
        return products.map(ProductMinResponse::new);
    }

    @Transactional(readOnly = true)
    public ProductDetailedResponse findById(final Long id) {
        Product product = getProductById(id);
        return new ProductDetailedResponse(product);
    }

    @Transactional
    public ProductDetailedResponse insert(final ProductRequest request) {
        Product product = new Product();
        copyDataFromRequest(request, product);
        product = productRepository.save(product);
        return new ProductDetailedResponse(product);
    }

    @Transactional
    public ProductDetailedResponse update(final Long id, final ProductRequest request) {
        Product product = getProductById(id);
        copyDataFromRequest(request, product);
        product = productRepository.save(product);
        return new ProductDetailedResponse(product);
    }

    public void deleteById(final Long id) {
        try {
           productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Product id " + id + " not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Data integrity violation");
        }
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product id " + id + " not found"));
    }

    private void copyDataFromRequest(final ProductRequest request, Product entity) {
        if (Objects.nonNull(request)) {
            BeanUtils.copyProperties(request, entity);

            entity.getCategories().clear();
            request.getCategories().forEach(cat -> {
                Category category = categoryRepository.findById(cat.getId())
                        .orElseThrow(() -> new NotFoundException("Category id " + cat.getId() + " not found"));
                entity.getCategories().add(category);
            });
        }
    }

}
