package com.flightontime.flightapi.controller;

import com.flightontime.flightapi.domain.usuario.Usuario;
import com.flightontime.flightapi.domain.usuario.UsuarioRepository;
import com.flightontime.flightapi.domain.usuario.UsuarioDetalheDados;
import com.flightontime.flightapi.domain.usuario.dto.CadastroUsuarioDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@SecurityRequirement(name = "bearer-key")

public class UsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public ResponseEntity<Page<UsuarioDetalheDados>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAll(paginacao).map(UsuarioDetalheDados::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<UsuarioDetalheDados>> listarAtivos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAllByAtivoTrue(paginacao).map(UsuarioDetalheDados::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetalheDados> detalhar(@PathVariable(name = "id") Long id){
        var usuario = repository.getReferenceById(id);
        return ResponseEntity.ok(new UsuarioDetalheDados(usuario));
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastroUsuarioDTO dto) {

        if (repository.existsByEmail(dto.email())) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setAtivo(true);

        repository.save(usuario);

        return ResponseEntity.ok().build();
    }
}

