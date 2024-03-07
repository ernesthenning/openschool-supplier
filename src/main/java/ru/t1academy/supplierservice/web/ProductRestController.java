package ru.t1academy.supplierservice.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.t1academy.supplierservice.dto.ProductTo;
import ru.t1academy.supplierservice.model.Product;
import ru.t1academy.supplierservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductRestController {

    private final ProductService service;

    @GetMapping("/{id}")
    public Product get(@PathVariable int id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PostMapping
    public Product create(@RequestBody ProductTo productTo) {
        return service.create(productTo);
    }

    @GetMapping
    public Page<Product> getAll(@RequestParam Integer offset) {
        return service.getAll(offset);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody ProductTo productTo, @PathVariable int id) {
        service.update(productTo, id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam("query") String query) {
        return ResponseEntity.ok(service.search(query));
    }
}
