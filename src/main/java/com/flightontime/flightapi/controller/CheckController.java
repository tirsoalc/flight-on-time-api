package com.flightontime.flightapi.controller;

import com.flightontime.flightapi.domain.usuario.UsuarioDetails;
import com.flightontime.flightapi.domain.usuario.UsuarioDetalheDados;
import com.flightontime.flightapi.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("check")
public class CheckController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<?> check(){
        Map<String, String> map = new HashMap<>();
        map.put("label", "nop");
        map.put("message","/check endpoint");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/who")
    @Hidden
    public ResponseEntity<?> who(){
        Map<String, Object> map = new HashMap<>();
        var usuarioAutenticado = (UsuarioDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        map.put("Username", usuarioAutenticado.getUsername());
        map.put("Authorities", usuarioAutenticado.getAuthorities());
        map.put("Password", usuarioAutenticado.getPassword());
        map.put("Usuario", new UsuarioDetalheDados(usuarioAutenticado.getUsuario()));

        return ResponseEntity.ok(map);
    }

    @GetMapping("/health")
    public ResponseEntity<?> status(){
        Map<String, String> map = new HashMap<>();
        map.put("label", "API healthcheck");
        map.put("message", "Healthy");
        return ResponseEntity.ok(map);
    }

}
