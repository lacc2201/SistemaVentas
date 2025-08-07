package com.sistemaventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sistemaventas.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {}
