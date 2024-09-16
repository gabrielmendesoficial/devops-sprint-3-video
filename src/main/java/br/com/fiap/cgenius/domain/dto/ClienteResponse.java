package br.com.fiap.cgenius.domain.dto;

import java.time.LocalDate;

import br.com.fiap.cgenius.domain.model.Cliente;

public record ClienteResponse(
    Long id,
    String nome,
    String cpf,
    LocalDate dtNascimento,
    String genero,
    String cep,
    String telefone,
    String email,
    String perfil
) {
    public static ClienteResponse from(Cliente cliente) {
        return new ClienteResponse(
            cliente.getId(),
            cliente.getNome(),
            cliente.getCpf(),
            cliente.getDtNascimento(),
            cliente.getGenero(),
            cliente.getCep(),
            cliente.getTelefone(),
            cliente.getEmail(),
            cliente.getPerfil()
        );
    }

}
