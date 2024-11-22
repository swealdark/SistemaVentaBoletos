package com.moon.SistemaVentaBoletos.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public Usuario registrarUsuario(Usuario usuario){
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        return usuarioRepository.save(usuario);
    }
    public Optional<Usuario> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
    public boolean verificarContraseña(String contraseña, String contraseñaHash){
        return passwordEncoder.matches(contraseña,contraseñaHash);
    }



}
