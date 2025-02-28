import org.example.Model.Product;
import org.example.StockObserver;
import org.example.WarningAgent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WarningAgentTest {

    private WarningAgent warningAgent;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        warningAgent = new WarningAgent();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void update_shouldWarnWhenStockBelow5() {
        // Given
        Product product = new Product("1", "Xbox One S", new BigDecimal("299.99"), 4, "Electronics");

        // When
        warningAgent.update(product);

        // Then
        String output = outputStream.toString();
        assertTrue(output.contains("ALERTA!!! El stock del Producto: Xbox One S es muy bajo, solo quedan 4 unidades."));

        // Restore System.out
        System.setOut(originalOut);
    }

    @Test
    void update_shouldNotWarnWhenStockAtOrAbove5() {
        // Given
        Product product = new Product("1", "Xbox One S", new BigDecimal("299.99"), 5, "Electronics");

        // When
        warningAgent.update(product);

        // Then
        String output = outputStream.toString();
        assertFalse(output.contains("ALERTA!!!"));

        // Restore System.out
        System.setOut(originalOut);
    }
}
