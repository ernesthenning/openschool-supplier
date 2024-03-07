DELETE FROM category;
DELETE FROM product;

INSERT INTO category (id, name)
VALUES (1, 'category1'),
       (2, 'category2'),
       (3, 'category3'),
       (4, 'category4'),
       (5, 'category5');


INSERT INTO product (id,name, description, price, category_id)
VALUES (1, 'product1', 'product1', 100, 1),
       (2, 'product2', 'product2', 1000, 1),
       (3, 'product3', 'product3', 100, 2),
       (4, 'product4', 'product4', 500, 2),
       (5, 'product5', 'product5', 100, 3),
       (6, 'product6', 'product6', 10, 3),
       (7, 'product7', 'product7', 10, 4),
       (8, 'product8', 'product8', 600, 4),
       (9, 'product9', 'product9', 700, 5),
       (10, 'product10', 'product10', 1500, 5);
