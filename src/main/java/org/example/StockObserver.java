package org.example;


import org.example.Model.Product;

public interface StockObserver {
    void update(Product product);
}