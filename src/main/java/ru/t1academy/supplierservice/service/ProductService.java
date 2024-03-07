package ru.t1academy.supplierservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.t1academy.supplierservice.dto.ProductTo;
import ru.t1academy.supplierservice.model.Product;
import ru.t1academy.supplierservice.repository.CategoryRepository;
import ru.t1academy.supplierservice.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public Product get(int id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Product create(ProductTo productTo) {
        Product product = new Product(productTo);
        product.setCategory(categoryRepository.getReferenceById(productTo.getCategoryId()));
        return repository.save(product);
    }

    public Page<Product> getAll(Integer offset) {
        return repository.findAll(PageRequest.of(offset, 5));
    }

    public void update(ProductTo productTo, int id) {
        if (productTo.getId() != null && productTo.getId() == id) {
            Product product = new Product(productTo);
            product.setCategory(categoryRepository.getReferenceById(productTo.getCategoryId()));
            repository.save(product);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<Product> search (String query) {
        return repository.search(query);
    }

}
