import org.example.LogAgent;
import org.example.Model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LogAgentTest {

    private LogAgent logAgent;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        logAgent = new LogAgent();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void update_shouldLogProductStock() {
        // Given
        Product product = new Product("1", "Xbox One S", new BigDecimal("299.99"), 10, "Electronics");

        // When
        logAgent.update(product);

        // Then
        String output = outputStream.toString();
        assertTrue(output.contains("Producto: Xbox One S -> 10 unidades disponibles"));

        // Restore System.out
        System.setOut(originalOut);
    }
}