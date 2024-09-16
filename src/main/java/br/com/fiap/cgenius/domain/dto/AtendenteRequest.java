package br.com.fiap.cgenius.domain.dto;

import br.com.fiap.cgenius.domain.model.Atendente;

public record AtendenteRequest(
        String nome,
        String cpf,
        String setor,
        String senha,
        String perfil
) {
    public Atendente toModel(){
        return Atendente.builder()
        .nome(nome)
        .cpf(cpf)
        .setor(setor)
        .senha(senha)
        .perfil(perfil)
        .build();
    }
    
}
