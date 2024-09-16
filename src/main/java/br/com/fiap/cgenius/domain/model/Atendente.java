package br.com.fiap.cgenius.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atendente")
public class Atendente {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_atendente")
    private Long id;

    @Column(name="nome_atendente")
    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @Column(name="cpf_atendente")
    @NotBlank(message = "Campo obrigatório")
    @Size(min=11,  message = "CPF Inválido")
    private String cpf;

    @Column(name="setor")
    @NotBlank(message = "Campo obrigatório")
    private String setor;

    @Column(name="senha")
    @NotBlank(message = "Campo obrigatório")    
    @Size(min = 6, message = "Mínimo de 6 caracteres")
    private String senha;

    @Column(name="perfil_atendente")
    private String perfil;   
}