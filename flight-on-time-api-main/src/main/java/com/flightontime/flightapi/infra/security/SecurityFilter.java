
package com.flightontime.flightapi.infra.security;

import com.flightontime.flightapi.domain.usuario.UsuarioDetails;
import com.flightontime.flightapi.domain.usuario.UsuarioRepository;
import com.flightontime.flightapi.domain.usuario.UsuarioDetalheDados;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("SecurityFilter.doFilterInternal: {}", request.getRequestURI());
        if (!Arrays.asList(SecurityConfigurations.ENDPOINTS_GET_NO_AUTH).contains(request.getRequestURI()) ||
                !Arrays.asList(SecurityConfigurations.ENDPOINTS_POST_NO_AUTH).contains(request.getRequestURI())) {

            log.info("SecurityFilter.doFilterInternal: Verificando token");
            var tokenJWT = recuperarToken(request);

            if (tokenJWT != null) {
                log.info("Token presente");
                var subject = tokenService.getSubject(tokenJWT);
                var usuario = repository.findByEmailAndAtivoTrue(subject);
                UsuarioDetails userDetails = new UsuarioDetails(usuario.get());
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

                UsuarioDetalheDados usuarioResponseDTO = new UsuarioDetalheDados(usuario.get());
                log.info("Token verificado: {} {}", usuarioResponseDTO.email(), usuarioResponseDTO.perfis().stream().map(p -> p.nome()).toList());

            }else{
                log.info("Token ausente");
            }

        }else{
            log.info("SecurityFilter.doFilterInternal: Endpoint não requer token");
        }
        log.info("SecurityFilter.doFilterInternal: continuando");
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

}

