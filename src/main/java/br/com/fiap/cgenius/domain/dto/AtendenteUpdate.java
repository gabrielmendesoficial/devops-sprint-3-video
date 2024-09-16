package br.com.fiap.cgenius.domain.dto;

import br.com.fiap.cgenius.domain.model.Atendente;

public record AtendenteUpdate(
    String nome,
    String setor,
    String senha,
    String perfil
) {
    public Atendente toModel(Atendente atendente) {
        if (nome != null) {
            atendente.setNome(nome);
        }
        if (setor != null) {
            atendente.setSetor(setor);
        }
        if (senha != null) {
            atendente.setSenha(senha);
        }
        if (perfil != null) {
            atendente.setPerfil(perfil);
        }
        return atendente;
    }
}
