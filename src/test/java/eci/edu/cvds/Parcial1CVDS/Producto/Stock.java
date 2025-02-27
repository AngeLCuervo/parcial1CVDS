package eci.edu.cvds.Parcial1CVDS;
import eci.edu.cvds.parcial1CVDS.Agente.Alerta; 
import eci.edu.cvds.parcialCVDS2025.Agente.Producto; 
import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import java.util.*;

@SpringBootApplication public class Parcial1CVDSApplication { 
private final Map<Producto, Integer> productos; private final List alertas;

public Parcial1cvdsApplication() {
    productos = new HashMap<>();
    alertas = new ArrayList<>();
}

public boolean addProducto(Producto producto){
    if (producto == null){
        return false;
    }
    if (productos.containsKey(producto)) {
        productos.put(producto, productos.get(producto) + 1);
        return true;
    } else {
        productos.put(producto, 1);
    }
    return true;
}

public static void main(String[] args) {

}