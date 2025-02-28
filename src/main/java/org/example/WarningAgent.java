package org.example;

import org.example.Model.Product;
import org.springframework.stereotype.Component;

@Component
public class WarningAgent implements StockObserver {

    private static final int STOCK_THRESHOLD = 5;

    @Override
    public void update(Product product) {
        if (product.getStock() < STOCK_THRESHOLD) {
            System.out.println("ALERTA!!! El stock del Producto: " + product.getName() +
                    " es muy bajo, solo quedan " + product.getStock() + " unidades.");
        }
    }
}