package ru.t1academy.supplierservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import ru.t1academy.supplierservice.dto.ProductTo;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="product")
public class Product extends AbstractEntity {

    public Product(ProductTo productTo) {
        this.id = productTo.getId();
        this.name = productTo.getName();
        this.description = productTo.getDescription();
        this.price = productTo.getPrice();
        this.categoryId = productTo.getCategoryId();
        this.category = null;
    }

    @Column(name="description")
    private String description;

    @Column(name="price")
    private float price;

    @Column(name="category_id", insertable = false, updatable = false)
    private Integer categoryId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

}
