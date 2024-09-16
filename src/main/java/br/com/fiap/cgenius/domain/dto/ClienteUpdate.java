package br.com.fiap.cgenius.domain.dto;

import br.com.fiap.cgenius.domain.model.Cliente;


public record ClienteUpdate(
    String nome,
    String telefone,
    String cep,
    String email
) {
    public Cliente toModel(Cliente cliente) {
        if (nome != null) {
            cliente.setNome(nome);
        }
        if (telefone != null) {
            cliente.setTelefone(telefone);
        }
        if (cep != null) {
            cliente.setCep(cep);
        }
        if (email != null) {
            cliente.setEmail(email);
        }
        return cliente;
    }
}
