package com.springcode.blog.controller;

import com.springcode.blog.dao.LoginDTO;
import com.springcode.blog.dao.RegistroDTO;
import com.springcode.blog.entity.Rol;
import com.springcode.blog.entity.Usuario;
import com.springcode.blog.repository.RolRepository;
import com.springcode.blog.repository.UsuarioRepository;
import com.springcode.blog.security.JWTAuthResponseDTO;
import com.springcode.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/iniciarSession")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /*
        Obtenemos el token de jwtTokenProvider
         */
        String token = jwtTokenProvider.generarToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO){
        if (usuarioRepository.existsByUsername(registroDTO.getUsername())) {
            return new ResponseEntity<>("Ese usuario ya se encuentra registrado",HttpStatus.BAD_REQUEST);
        }

        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            return new ResponseEntity<>("Ese email de usuario ya se encuentra registrado",HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Rol roles = rolRepository.findByNombre("ROLE_ADMIN").get();
        usuario.setRoles(Collections.singleton(roles));

        usuarioRepository.save(usuario);

        return new ResponseEntity<>("Usuario registrado exitosamente!",HttpStatus.OK);
    }
}
