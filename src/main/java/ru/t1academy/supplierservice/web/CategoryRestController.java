package ru.t1academy.supplierservice.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.t1academy.supplierservice.model.Category;
import ru.t1academy.supplierservice.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryRestController {

    private final CategoryService service;

    @GetMapping("/{id}")
    public Category get(@PathVariable int id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return service.create(category);
    }

    @GetMapping
    public List<Category> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Category category, @PathVariable int id) {
        service.update(category, id);
    }
}
