package ru.t1academy.supplierservice.web;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.t1academy.supplierservice.SupplierserviceApplication;
import ru.t1academy.supplierservice.model.Category;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SupplierserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerIntegrationTest {

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @LocalServerPort
    protected int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    public void testGetAllCategories() {
        ResponseEntity<List> responseEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/api/v1/categories", List.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
        assertEquals(5, responseEntity.getBody().size());
    }

    @Test
    public void givenId_whenGetById_thenGetId() {
        ResponseEntity<Category> responseEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/api/v1/categories/1", Category.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
        assertEquals(1, responseEntity.getBody().getId());
    }

    @Test
    public void givenId_whenDeleteById_thenGetById() {
        testRestTemplate.delete("http://localhost:" + port + "/api/v1/categories/5");
        assertNull(this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/v1/categories/5", Category.class));
    }

    @Test
    public void givenNewCategory_whenCreateCategory_thenCheckStatusAndName() {
        Category newCategory = new Category();
        newCategory.setName("testCategory");
        ResponseEntity<Category> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/api/v1/categories", newCategory, Category.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
        assertEquals("testCategory", responseEntity.getBody().getName());
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
