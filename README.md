# Parcial cvds 

## NECESIDAD DEL CLIENTE

El cliente necesita un sistema de monitoreo de stock de productos, el cual le permita agregar productos nuevos y actualizar la cantidad de productos disponibles. Adicionalmente cada vez que un producto sea actualizado es necesario que se notifique a los dos agentes que serán implementados; Para los agentes es necesario tener en cuenta las siguientes características, el primero deberá escribir en el stdout las unidades disponibles y el segundo agente deberá escribir en el stdout si hay menos de 5 unidades disponibles lo cual generará una alerta.

## REQUERIMIENTO

1. FUNCIONALES
Añadir un producto: los productos deben tener nombre, precio, cantidad en stock y categoría.

2. Modificar stock: Se debe actualizar la cantidad de producto disponible y adicionalmente se debe notificar a los interesados.
Notificar el cambio de stock: Los agentes se deben ejecutar según los requerimientos de cada uno, cuando el stock de cualquier producto se vea afectado.

## DESCRIPCIÓN DEL PROYECTO

Se debe crear un repositorio en GitHub el cual debe tener un proyecto maven que funcione con spring-boot, este proyecto deberá darle solución a los requerimientos del cliente y seguir los principios SOLID. Se debe implementar por lo menos un patrón de diseño, usar la inyección de dependencias para instaciar objetos y es necesario Que las pruebas de unidad reflejen el correcto funcionamiento de los agentes.

## Diseño del Sistema

### Modelo de Dominio
- **Product**: Entidad principal con atributos nombre, precio, cantidad en stock y categoría
- **StockObserver**: Interfaz para los observadores (agentes)
- **StockManager**: Servicio que gestiona los productos y notifica a los observadores

### Arquitectura
El sistema sigue una arquitectura basada en capas, utilizando Spring Boot como framework principal:
- **Capa de Presentación**: Controladores REST para la API
- **Capa de Servicio**: Lógica de negocio
- **Capa de Datos**: Repositorios para acceso a datos (en memoria)

### Directorios

![image](https://github.com/user-attachments/assets/7f090dcb-189d-4836-ab64-79572023fa47)

## Pruebas de unidad 

![image](https://github.com/user-attachments/assets/d97f23ee-9611-48bf-9b84-74f5c224fb0c)


