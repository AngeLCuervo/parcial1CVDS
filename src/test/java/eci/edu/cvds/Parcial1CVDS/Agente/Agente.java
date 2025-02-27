alerta package eci.edu.cvds.Parcial1CVDS.Agente;

public class Alerta{ private String mensaje;
public Alerta(String mensaje) {
    this.mensaje = mensaje;
}

public String getMensaje() {
    return mensaje;
}

public String productoAgotado(Producto producto) {
    if (producto.getCantidad() < 5) {
        mensaje = "ALERTA!!! El stock del Producto " + "" + producto.getName() + "" + "es muy bajo, solo quedan" + "" + producto.getCantidad();
        return mensaje;
    }
    return mensaje;
}