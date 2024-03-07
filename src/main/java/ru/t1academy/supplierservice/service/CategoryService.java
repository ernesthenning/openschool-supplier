package ru.t1academy.supplierservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.t1academy.supplierservice.model.Category;
import ru.t1academy.supplierservice.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category get(int id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Category create(Category category) {
        return repository.save(category);
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public void update(Category category, int id) {
        if (category.getId() != null && category.getId() == id) {
            repository.save(category);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
