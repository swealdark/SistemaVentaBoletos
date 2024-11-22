package com.moon.SistemaVentaBoletos.infra.security;

import com.moon.SistemaVentaBoletos.infra.JwtUtil;
import com.moon.SistemaVentaBoletos.usuario.UsuarioRepository;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        var authHeader = request.getHeader("Authorization");
        var requestPath = request.getServletPath();
        if(requestPath.equals("/api/auth/login") || requestPath.equals("/api/auth/registro")){
            filterChain.doFilter(request,response);
            return;
        }
        if(authHeader != null ){
            var token = authHeader.replace("Bearer ","");
            var nombreUsuario = jwtUtil.extraerEmail(token);
            if(nombreUsuario != null){
                var usuario = usuarioRepository.findByEmail(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario,null,
                        usuario.get().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request,response);
        }

    }

}
