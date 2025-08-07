package com.sistemaventas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemaventas.model.Vendedor;
import com.sistemaventas.repository.VendedorRepository;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> listarTodos() {
        return vendedorRepository.findAll();
    }

    public Optional<Vendedor> obtenerPorId(Long id) {
        return vendedorRepository.findById(id);
    }

    public Vendedor guardar(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public void eliminar(Long id) {
        vendedorRepository.deleteById(id);
    }
}