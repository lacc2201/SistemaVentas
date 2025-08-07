package com.sistemaventas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/conexion")
    public String probarConexion() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return "✅ Conexión a la base de datos exitosa";
        } catch (Exception e) {
            return "❌ Error de conexión: " + e.getMessage();
        }
    }
}
