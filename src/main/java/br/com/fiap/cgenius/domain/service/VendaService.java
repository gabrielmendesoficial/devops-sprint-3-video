package br.com.fiap.cgenius.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.cgenius.domain.model.Atendente;
import br.com.fiap.cgenius.domain.model.Cliente;
import br.com.fiap.cgenius.domain.model.Plano;
import br.com.fiap.cgenius.domain.model.Script;
import br.com.fiap.cgenius.domain.model.Venda;
import br.com.fiap.cgenius.domain.repository.AtendenteRepository;
import br.com.fiap.cgenius.domain.repository.ClienteRepository;
import br.com.fiap.cgenius.domain.repository.PlanoRepository;
import br.com.fiap.cgenius.domain.repository.ScriptRepository;
import br.com.fiap.cgenius.domain.repository.VendaRepository;

@Service
public class VendaService {
    @Autowired
    VendaRepository vendaRepository;
    
    @Autowired
    AtendenteRepository atendenteRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ScriptRepository scriptRepository;

    @Autowired
    PlanoRepository planoRepository;

    public List<Venda> findAll(){
        return vendaRepository.findAll();
    }

    public Venda create(Venda venda){
        Optional<Atendente> atendente = atendenteRepository.findById(venda.getAtendente().getId());
        Optional<Cliente> cliente = clienteRepository.findById(venda.getCliente().getId());
        Optional<Script> script = scriptRepository.findById(venda.getScript().getId());
        Optional<Plano> plano = planoRepository.findById(venda.getPlano().getId());
        if(atendente.isPresent() && cliente.isPresent() && script.isPresent() && plano.isPresent()){
            return vendaRepository.save(venda);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Atendente, Cliente, Script ou Plano não encontrado");
        }
    }

    public Venda findById(Long id){
        return vendaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id não encontrado"));
    }

    public List<Venda> findByCpfAtendente(String cpfAtendente){
        List<Venda> venda = vendaRepository.findByCpfAtendente(cpfAtendente);
        if (venda != null) {
            return venda;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrado");
        }
    }
    public List<Venda> findByCpfCliente(String cpfCliente){
        List<Venda> venda = vendaRepository.findByCpfCliente(cpfCliente);
        if (venda != null) {
            return venda;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrado");
        }
    }
    public Venda findByIdScript(Long id){
        Venda venda = vendaRepository.findByIdScript(id);
        if (venda != null) {
            return venda;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrado");
        }
    }
    public List<Venda> findByIdPlano(Long id){
        List<Venda> venda = vendaRepository.findByIdPlano(id);
        if (venda != null) {
            return venda;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Especificacão não encontrado");
        }
    }

    public void delete(Long id){
        verificarExistencia(id);
        vendaRepository.deleteById(id);
    }

    public Venda update(Long id, Venda venda){
        verificarExistencia(id);
        venda.setId(id);
        return vendaRepository.save(venda);
    }

    private void verificarExistencia(Long id){
        vendaRepository.
        findById(id)
        .orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "id não encontrado")
        );
    }
}
