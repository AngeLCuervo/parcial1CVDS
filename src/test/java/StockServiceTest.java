import org.example.Model.Product;
import org.example.Repository.ProductRepository;
import org.example.Service.StockService;
import org.example.StockObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StockServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockObserver observer1;

    @Mock
    private StockObserver observer2;

    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<StockObserver> observers = Arrays.asList(observer1, observer2);
        stockService = new StockService(productRepository, observers);
    }

    @Test
    void addProduct_shouldSaveProduct() {
        // Given
        Product product = new Product("1", "Nintendo Switch", new BigDecimal("299.99"), 30, "Electronics");
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        Product result = stockService.addProduct(product);

        // Then
        assertEquals(product, result);
        verify(productRepository).save(product);
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        // Given
        List<Product> products = Arrays.asList(
                new Product("1", "Nintendo Switch", new BigDecimal("299.99"), 30, "Electronics"),
                new Product("2", "PS5", new BigDecimal("499.99"), 10, "Electronics")
        );
        when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = stockService.getAllProducts();

        // Then
        assertEquals(products, result);
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_shouldReturnProduct() {
        // Given
        Product product = new Product("1", "Nintendo Switch", new BigDecimal("299.99"), 30, "Electronics");
        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        // When
        Optional<Product> result = stockService.getProductById("1");

        // Then
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository).findById("1");
    }

    @Test
    void updateStock_shouldUpdateAndNotifyObservers() {
        // Given
        Product product = new Product("1", "Nintendo Switch", new BigDecimal("299.99"), 30, "Electronics");
        Product updatedProduct = new Product("1", "Nintendo Switch", new BigDecimal("299.99"), 25, "Electronics");

        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // When
        Product result = stockService.updateStock("1", 25);

        // Then
        assertEquals(25, result.getStock());
        verify(productRepository).findById("1");
        verify(productRepository).save(product);
        verify(observer1).update(updatedProduct);
        verify(observer2).update(updatedProduct);
    }

    @Test
    void updateStock_shouldThrowExceptionWhenProductNotFound() {
        // Given
        when(productRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            stockService.updateStock("nonexistent", 10);
        });

        assertTrue(exception.getMessage().contains("Producto no encontrado"));
    }

    @Test
    void registerObserver_shouldAddNewObserver() {
        // Given
        StockObserver newObserver = mock(StockObserver.class);

        // When
        stockService.registerObserver(newObserver);

        // Then
        Product product = new Product("1", "Test", new BigDecimal("10"), 5, "Test");
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        stockService.updateStock("1", 5);

        verify(newObserver).update(product);
    }

    @Test
    void removeObserver_shouldRemoveExistingObserver() {
        // When
        stockService.removeObserver(observer2);

        // Then
        Product product = new Product("1", "Test", new BigDecimal("10"), 5, "Test");
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        stockService.updateStock("1", 5);

        verify(observer1).update(product);
        verify(observer2, never()).update(product);
    }
}