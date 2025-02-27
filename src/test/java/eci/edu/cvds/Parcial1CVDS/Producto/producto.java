public Producto(String name, int cantidad, int precio, String categoria){ this.name = name; this.cantidad = cantidad; this.categoria = categoria; this.precio = precio; }

/**El resultado que de el nombre del producto 
 * 
 * */ 

public String getName(){return name;}

public int getCantidad(){return cantidad; }

public String getCategoria(){return categoria;}

public int getPrecio(){return precio; }

public void aumentarCantidad(){ }

public String modificarProducto(Producto Producto){ Producto = new 
Producto(name, cantidad, precio, categoria); if (producto.getCantidad() == 0){
return null; } return null; } 