package br.com.fiap.cgenius.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "script")
public class Script {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_script")
    private Long id;

    @Column(name="descricao_script")
    @NotBlank(message="Campo Obrigatório")
    private String descricaoScript;

    @ManyToOne
    @JoinColumn(name = "id_plano")
    private Plano plano;
}
