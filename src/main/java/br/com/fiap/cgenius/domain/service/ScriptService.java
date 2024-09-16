package br.com.fiap.cgenius.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.cgenius.domain.model.Script;
import br.com.fiap.cgenius.domain.repository.PlanoRepository;
import br.com.fiap.cgenius.domain.repository.ScriptRepository;

@Service
public class ScriptService {

    @Autowired
    ScriptRepository scriptRepository;
    @Autowired
    PlanoRepository  planoRepository;
    
    public List<Script> findAll(){
        return scriptRepository.findAll();
    }

    public Script create(Script script){
        if(!planoRepository.findById(script.getPlano().getId()).isEmpty()){
            return scriptRepository.save(script);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado");
        }
    }

    public Script findById(Long id){
        return scriptRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id não encontrado"));
    }
    
    public void delete(Long id){
        verificarExistencia(id);
        scriptRepository.deleteById(id);
    }

    public Script update(Long id, Script script){
        verificarExistencia(id);
        script.setId(id);
        return scriptRepository.save(script);
    }

    private void verificarExistencia(Long id){
        scriptRepository.
        findById(id)
        .orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "id não encontrado")
        );
    }   
}