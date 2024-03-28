package ru.t1academy.supplierservice.web;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.t1academy.supplierservice.SupplierserviceApplication;
import ru.t1academy.supplierservice.dto.ProductTo;
import ru.t1academy.supplierservice.model.Product;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = SupplierserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    public void testGetAllProducts() {
        assertEquals(5, this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/products?offset=0", RestResponsePage.class)
                .getNumberOfElements());
    }

    @Test
    public void givenId_whenGetById_thenGetId() {
        assertEquals(1, (int) this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/products/1", Product.class)
                .getId());
    }

    @Test
    public void givenId_whenDeleteById_thenGetById() {
        testRestTemplate.delete("http://localhost:" + port + "/api/v1/products/5");
        assertNull(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/products/5", Product.class));
    }

    @Test
    public void givenNewProduct_whenCreateProduct_thenCompareProductNames() {
        ProductTo newProduct = new ProductTo();
        newProduct.setName("testProduct");
        newProduct.setCategoryId(1);
        newProduct.setDescription("testDescription");
        newProduct.setPrice(1);
        assertEquals("testProduct", testRestTemplate
                .postForObject("http://localhost:" + port + "/api/v1/products", newProduct, Product.class).getName());
    }

    @Test
    public void givenProduct_whenUpdateName_thenGetAndCompareName() {
        String newName = "newName";
        Product product = this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/products/2", Product.class);
        product.setName(newName);
        this.testRestTemplate.put("http://localhost:" + port + "/api/v1/products/2", product);
        assertEquals(newName, this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/products/2", Product.class).getName());
    }

}
