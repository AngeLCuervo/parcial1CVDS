import org.example.Controller.ProductController;
import org.example.Model.Product;
import org.example.Service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private StockService stockService;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(stockService);
    }

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        // Given
        Product product = new Product(null, "Laptop Dell XPS", new BigDecimal("1299.99"), 10, "Computers");
        Product savedProduct = new Product("1", "Laptop Dell XPS", new BigDecimal("1299.99"), 10, "Computers");
        when(stockService.addProduct(any(Product.class))).thenReturn(savedProduct);

        // When
        ResponseEntity<Product> response = productController.createProduct(product);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedProduct, response.getBody());
        verify(stockService).addProduct(product);
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        // Given
        List<Product> products = Arrays.asList(
                new Product("1", "Laptop Dell XPS", new BigDecimal("1299.99"), 10, "Computers"),
                new Product("2", "MacBook Pro", new BigDecimal("1999.99"), 5, "Computers")
        );
        when(stockService.getAllProducts()).thenReturn(products);

        // When
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(stockService).getAllProducts();
    }

    @Test
    void getProductById_shouldReturnProductWhenExists() {
        // Given
        Product product = new Product("1", "Laptop Dell XPS", new BigDecimal("1299.99"), 10, "Computers");
        when(stockService.getProductById("1")).thenReturn(Optional.of(product));

        // When
        ResponseEntity<Product> response = productController.getProductById("1");

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(stockService).getProductById("1");
    }

    @Test
    void getProductById_shouldReturnNotFoundWhenNotExists() {
        // Given
        when(stockService.getProductById("nonexistent")).thenReturn(Optional.empty());

        // When
        ResponseEntity<Product> response = productController.getProductById("nonexistent");

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(stockService).getProductById("nonexistent");
    }

    @Test
    void updateStock_shouldReturnUpdatedProduct() {
        // Given
        Product updatedProduct = new Product("1", "Laptop Dell XPS", new BigDecimal("1299.99"), 8, "Computers");
        when(stockService.updateStock("1", 8)).thenReturn(updatedProduct);

        // When
        ResponseEntity<Product> response = productController.updateStock("1", 8);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
        verify(stockService).updateStock("1", 8);
    }

    @Test
    void updateStock_shouldReturnNotFoundWhenProductNotFound() {
        // Given
        when(stockService.updateStock("nonexistent", 5)).thenThrow(new RuntimeException("Producto no encontrado"));

        // When
        ResponseEntity<Product> response = productController.updateStock("nonexistent", 5);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(stockService).updateStock("nonexistent", 5);
    }
}