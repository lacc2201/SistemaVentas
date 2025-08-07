package com.sistemaventas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venta;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Vendedor vendedor;

    @Column(nullable = false)
    private Integer cantidad;

    private LocalDateTime fecha = LocalDateTime.now();

    public Venta() {}

    public Venta(Producto producto, Vendedor vendedor, Integer cantidad) {
        this.producto = producto;
        this.vendedor = vendedor;
        this.cantidad = cantidad;
    }

    public Long getId() { return id_venta; }
    public void setId(Long id_venta) { this.id_venta = id_venta; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Vendedor getVendedor() { return vendedor; }
    public void setVendedor(Vendedor vendedor) { this.vendedor = vendedor; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
