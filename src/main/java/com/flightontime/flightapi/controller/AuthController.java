package com.flightontime.flightapi.controller;

import com.flightontime.flightapi.domain.user.AuthData;
import com.flightontime.flightapi.domain.user.UserDetailsImpl;
import com.flightontime.flightapi.infra.security.TokenDataResponse;
import com.flightontime.flightapi.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDataResponse> login(@RequestBody @Valid AuthData data){
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = manager.authenticate(authenticationToken);
        var jwtToken = tokenService.generateToken((UserDetailsImpl) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDataResponse(jwtToken));
    }
}
