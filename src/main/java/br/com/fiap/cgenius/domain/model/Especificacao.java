package br.com.fiap.cgenius.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "especificacao")
public class Especificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_especificacao")
    private Long id;

    @Column(name="tipo_cartao_credito")
    @NotBlank(message = "Campo obrigatório")
    private String tipoCartaoCredito;

    @Column(name="gasto_mensal")
    @PositiveOrZero(message = "O valor do produto deve ser positivo")
    private BigDecimal gastoMensal;

    @Column(name="renda_mensal")
    @PositiveOrZero(message = "O valor do produto deve ser positivo")
    private BigDecimal rendaMensal;

    @Column(name="viaja_frequentemente")
    private Integer viajaFrequentemente;

    @NotBlank(message = "Campo obrigatório")
    private String interesses;

    @NotBlank(message = "Campo obrigatório")
    private String profissao;

    private Integer dependentes;

    @OneToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
