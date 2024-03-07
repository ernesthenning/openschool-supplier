package ru.t1academy.supplierservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.t1academy.supplierservice.model.AbstractEntity;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductTo extends AbstractEntity {

    private String description;

    private float price;

    private Integer categoryId;
}
