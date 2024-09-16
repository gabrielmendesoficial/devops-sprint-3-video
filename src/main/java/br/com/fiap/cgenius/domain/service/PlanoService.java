package br.com.fiap.cgenius.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.cgenius.domain.model.Plano;
import br.com.fiap.cgenius.domain.repository.PlanoRepository;

@Service
public class PlanoService {
    @Autowired
    PlanoRepository planoRepository;

    public List<Plano> findAll(){
        return planoRepository.findAll();
    }

    public Plano create(Plano plano){
        return planoRepository.save(plano);
    }

    public Plano findById(Long id){
        return planoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id não encontrado"));
    }
    
    public void delete(Long id){
        verificarExistencia(id);
        planoRepository.deleteById(id);
    }

    public Plano update(Long id, Plano plano){
        verificarExistencia(id);
        plano.setId(id);
        return planoRepository.save(plano);
    }

    private void verificarExistencia(Long id){
        planoRepository.
        findById(id)
        .orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "id não encontrado")
        );
    }

}
