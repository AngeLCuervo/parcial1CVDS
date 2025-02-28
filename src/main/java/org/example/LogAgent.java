package org.example;


import org.example.Model.Product;
import org.springframework.stereotype.Component;

@Component
public class LogAgent implements StockObserver {

    @Override
    public void update(Product product) {
        System.out.println("Producto: " + product.getName() + " -> " + product.getStock() + " unidades disponibles");
    }
}