package br.com.fiap.cgenius.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.cgenius.domain.repository.AtendenteRepository;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final AtendenteRepository atendenteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(TokenService tokenService, AtendenteRepository atendenteRepository, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.atendenteRepository = atendenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Token login(Credentials credentials){
        var atendente = atendenteRepository.findByCpf(credentials.cpf());
        if(atendente == null){
            throw new RuntimeException("Atendente não encontrado");
        }
        if(passwordEncoder.matches(credentials.senha(), atendente.getSenha())){
            return tokenService.createToken(credentials.cpf());
        }
        throw new RuntimeException("Senha inválida");
    }


}
