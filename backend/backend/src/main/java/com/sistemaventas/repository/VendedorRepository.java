package com.sistemaventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaventas.model.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    // Spring se encarga del CRUD básico
}