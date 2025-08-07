package com.sistemaventas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaventas.model.Vendedor;
import com.sistemaventas.service.VendedorService;

@RestController
@RequestMapping("/api/vendedores")
@CrossOrigin(origins = "*") // Permite que el frontend consuma la API
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public List<Vendedor> listar() {
        return vendedorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Vendedor> obtener(@PathVariable Long id) {
        return vendedorService.obtenerPorId(id);
    }

    @PostMapping
    public Vendedor guardar(@RequestBody Vendedor vendedor) {
        return vendedorService.guardar(vendedor);
    }

    @PutMapping("/{id}")
    public Vendedor actualizar(@PathVariable Long id, @RequestBody Vendedor vendedor) {
        vendedor.setId(id);
        return vendedorService.guardar(vendedor);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        vendedorService.eliminar(id);
    }
}
