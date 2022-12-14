package modelo2;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private List<Producto> productos;

    public List<Producto> productos() {
        return productos;
    }

    public Carrito() {
        this.productos = new ArrayList<>();
        //    this.cliente = cliente;
    }

    public void agregarProductoAlCarrito(Producto producto) {
        if (producto == null)
            throw new RuntimeException("El producto a agregar no puede ser vacio.");
        this.productos.add(
                new Producto(producto.codigo(), producto.precio(),
                        producto.descripcion(), producto.categoria(),
                        producto.marca()));
    }


    public double calcularMontoCarrito(MarcaPromocion marcaPromocion, TarjetaPromocion tarjetaPromocion, Tarjeta tarjeta) {

        if (marcaPromocion == null || tarjetaPromocion == null)
            throw new RuntimeException("Las promociones no pueden ser vacias.");
        if (!marcaPromocion.fechaValida() || !tarjetaPromocion.fechaValida())
            throw new RuntimeException("Las promociones no tienen fechas validas.");
        if (tarjeta == null)
            throw new RuntimeException("La tarjeta para calcular el monto no puede ser vacia.");

        double precio = 0;
        for (Producto producto : this.productos) {
            if (producto.marca().equals(marcaPromocion.marca())) {

                precio = precio + (producto.precio() - (producto.precio() * marcaPromocion.descuento()));
            } else {
                precio += producto.precio();
            }
        }
        if (tarjeta.tipoTarjeta().equals(tarjetaPromocion.tarjeta())) {
            precio = precio - (precio * tarjetaPromocion.descuento());
        }

        return precio;

    }

    public Venta pagar(Cliente cliente, MarcaPromocion marcaPromocion, TarjetaPromocion tarjetaPromocion, Tarjeta tarjeta) {

        if (tarjeta != null) { //deberia ser la validacion del servicio
            return new Venta(cliente, tarjeta, EstadoVenta.COMPLETA, productos, calcularMontoCarrito(marcaPromocion, tarjetaPromocion, tarjeta));
        }
        return new Venta(cliente, tarjeta, EstadoVenta.CANCELADA, productos, 0);
    }

   /* @Override
    public String toString() {
        return "Carrito{ " + productos +
                " }";
    }*/
}
