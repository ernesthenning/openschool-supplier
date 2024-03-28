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
import ru.t1academy.supplierservice.model.Category;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SupplierserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerIntegrationTest {

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
    public void testGetAllCategories() {
        assertEquals(5, this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/categories", ArrayList.class)
                .size());
    }

    @Test
    public void givenId_whenGetById_thenGetId() {
        assertEquals(1, (int) this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/categories/1", Category.class)
                .getId());
    }

    @Test
    public void givenId_whenDeleteById_thenGetById() {
        testRestTemplate.delete("http://localhost:" + port + "/api/v1/categories/5");
        assertNull(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/categories/5", Category.class));
    }

    @Test
    public void givenNewCategory_whenCreateCategory_thenCompareCategoryNames() {
        Category newCategory = new Category();
        newCategory.setName("testCategory");
        assertEquals("testCategory", testRestTemplate.postForObject("http://localhost:" + port + "/api/v1/categories", newCategory, Category.class).getName());
    }

    @Test
    public void givenCategory_whenUpdateName_thenGetAndCompareName() {
        String newName = "newName";
        Category category = this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/categories/2", Category.class);
        category.setName(newName);
        this.testRestTemplate.put("http://localhost:" + port + "/api/v1/categories/2", category);
        assertEquals(newName, this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/categories/2", Category.class).getName());
    }

}
