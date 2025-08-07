package com.sistemaventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sistemaventas.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}
