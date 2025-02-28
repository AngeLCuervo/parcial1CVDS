import org.example.Model.Product;
import org.example.Repository.InMemoryProductRepository;
import org.example.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
    }

    @Test
    void save_shouldAssignIdWhenNull() {
        // Given
        Product product = new Product(null, "PlayStation 5", new BigDecimal("499.99"), 20, "Electronics");

        // When
        Product savedProduct = repository.save(product);

        // Then
        assertNotNull(savedProduct.getId());
    }

    @Test
    void save_shouldUpdateExistingProduct() {
        // Given
        Product product = new Product("1", "PlayStation 5", new BigDecimal("499.99"), 20, "Electronics");
        repository.save(product);

        // When
        product.setStock(15);
        Product updatedProduct = repository.save(product);

        // Then
        assertEquals(15, updatedProduct.getStock());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById_shouldReturnProductWhenExists() {
        // Given
        Product product = new Product("1", "PlayStation 5", new BigDecimal("499.99"), 20, "Electronics");
        repository.save(product);

        // When
        Optional<Product> result = repository.findById("1");

        // Then
        assertTrue(result.isPresent());
        assertEquals("PlayStation 5", result.get().getName());
    }

    @Test
    void findById_shouldReturnEmptyWhenNotExists() {
        // When
        Optional<Product> result = repository.findById("nonexistent");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        // Given
        repository.save(new Product("1", "PlayStation 5", new BigDecimal("499.99"), 20, "Electronics"));
        repository.save(new Product("2", "Xbox Series X", new BigDecimal("499.99"), 15, "Electronics"));

        // When
        List<Product> products = repository.findAll();

        // Then
        assertEquals(2, products.size());
    }

    @Test
    void delete_shouldRemoveProduct() {
        // Given
        repository.save(new Product("1", "PlayStation 5", new BigDecimal("499.99"), 20, "Electronics"));

        // When
        repository.delete("1");

        // Then
        assertEquals(0, repository.findAll().size());
    }
}