package br.com.fiap.cgenius.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.cgenius.domain.model.Script;

public interface ScriptRepository extends JpaRepository<Script, Long> {

}
