package com.moon.SistemaVentaBoletos.usuario;


import com.moon.SistemaVentaBoletos.infra.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    public AuthController(UsuarioService usuarioService,JwtUtil jwtUtil){
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@Valid @RequestBody Usuario usuario){
        if(usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Usuario newUsuario = usuarioService.registrarUsuario(usuario);
        return new ResponseEntity<>(newUsuario,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> autenticarUsuario(@RequestBody Usuario usuario){
        Optional<Usuario> usuarioOptional= usuarioService.buscarPorEmail(usuario.getEmail());
        if (usuarioOptional.isPresent() && usuarioService.verificarContraseña(usuario.getContraseña(), usuarioOptional.get().getContraseña())){
            String token = jwtUtil.generarToken(usuarioOptional.get().getEmail());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

}
