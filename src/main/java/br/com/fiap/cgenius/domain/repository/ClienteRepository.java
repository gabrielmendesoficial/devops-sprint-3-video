package br.com.fiap.cgenius.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.cgenius.domain.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByCpf(String cpf);
    void deleteByCpf(String cpf);

}
