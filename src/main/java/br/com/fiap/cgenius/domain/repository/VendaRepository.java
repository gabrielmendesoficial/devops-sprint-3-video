package br.com.fiap.cgenius.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.cgenius.domain.model.Venda;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    @Query("SELECT v FROM Venda v WHERE v.atendente.cpf = :cpf")
    List<Venda> findByCpfAtendente(String cpf);

    @Query("SELECT v FROM Venda v WHERE v.cliente.cpf = :cpf")
    List<Venda> findByCpfCliente(String cpf);

    @Query("SELECT v FROM Venda v WHERE v.script.id = :id")
    Venda findByIdScript(Long id);

    @Query("SELECT v FROM Venda v WHERE v.plano.id = :id")
    List<Venda> findByIdPlano(Long id);

}
