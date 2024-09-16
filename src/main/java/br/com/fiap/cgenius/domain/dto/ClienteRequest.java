package br.com.fiap.cgenius.domain.dto;

import java.time.LocalDate;

import br.com.fiap.cgenius.domain.model.Cliente;

public record ClienteRequest(
    String nome,
    String cpf,
    LocalDate dtNascimento,
    String genero,
    String cep,
    String telefone,
    String email,
    String perfil
    
) {
    public Cliente toModel(){
        return Cliente.builder()
        .nome(nome)
        .cpf(cpf)
        .dtNascimento(dtNascimento)
        .genero(genero)
        .cep(cep)
        .telefone(telefone)
        .email(email)
        .perfil(perfil)
        .build();
    }
}
