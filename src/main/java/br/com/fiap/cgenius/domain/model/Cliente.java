package br.com.fiap.cgenius.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_cliente")
    private Long id;
    
    @Column(name="nome_cliente")
    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @Column(name="cpf_cliente")
    @NotBlank(message = "Campo obrigatório")
    @Size(min=11,  message = "CPF Inválido")
    private String cpf;

    @Column(name="dt_nascimento")
    @NotNull(message="Campo Obrigatório")
    private LocalDate dtNascimento;

    @Column(name="genero")
    private String genero;

    @Column(name="cep")
    @NotBlank(message = "Campo obrigatório")
    @Size(min=8, max=8, message = "Cep Inválido")
    private String cep;

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 9, message = "Telefone Inválido")
    private String telefone;

    @NotBlank(message = "Campo obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @Column(name="perfil_cliente")
    @NotBlank(message = "Campo obrigatório")
    private String perfil;
}
