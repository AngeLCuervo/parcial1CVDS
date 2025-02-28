package org.example.Service;

import org.example.Model.Product;
import org.example.Repository.ProductRepository;
import org.example.StockObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class StockService {

    private final ProductRepository productRepository;
    private final Set<StockObserver> observers = new CopyOnWriteArraySet<>();

    @Autowired
    public StockService(ProductRepository productRepository, List<StockObserver> observerList) {
        this.productRepository = productRepository;
        if (observerList != null) {
            this.observers.addAll(observerList);
        }
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product updateStock(String id, int newStock) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStock(newStock);
            Product updatedProduct = productRepository.save(product);
            notifyObservers(updatedProduct);
            return updatedProduct;
        }
        throw new RuntimeException("Producto no encontrado con ID: " + id);
    }

    public void registerObserver(StockObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Product product) {
        observers.forEach(observer -> observer.update(product));
    }
}