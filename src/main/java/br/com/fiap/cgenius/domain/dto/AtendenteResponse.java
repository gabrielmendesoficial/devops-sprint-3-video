package br.com.fiap.cgenius.domain.dto;

import br.com.fiap.cgenius.domain.model.Atendente;

public record AtendenteResponse(
    Long id,
    String nome,
    String cpf,
    String setor,
    String perfil
) {
    public static AtendenteResponse from(Atendente atendente){
        return new AtendenteResponse(
            atendente.getId(),
            atendente.getNome(),
            atendente.getCpf(),
            atendente.getSetor(),
            atendente.getPerfil()
        );
    }

}
