package br.com.fiap.cgenius.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.cgenius.domain.model.Cliente;
import br.com.fiap.cgenius.domain.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente create(Cliente cliente){
        if (clienteRepository.findByCpf(cliente.getCpf()) == null ){
            return clienteRepository.save(cliente);
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já cadastrado");
        }
    }

    public Cliente findById(Long id){
        return clienteRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Não encontrado cliente com o id: " + id));
    }

    public Cliente findByCpf(String cpfCliente){
        Cliente cliente = clienteRepository.findByCpf(cpfCliente);
    if (cliente != null) {
        return cliente;
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
    }
    }

    public Cliente update(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id){
        verificarId(id);
        clienteRepository.deleteById(id);
    }

    public void deleteByCpf(String cpfCliente){
        verificarCpf(cpfCliente);
        clienteRepository.deleteByCpf(cpfCliente);
    }

    private void verificarId(Long id){
        clienteRepository.
        findById(id)
        .orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "id não encontrado")
        );
    }

    private Cliente verificarCpf(String cpfCliente){
        Cliente cliente = clienteRepository.findByCpf(cpfCliente);
    if (cliente == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente com CPF não encontrado");
    }else{
        return cliente;
    }
}
}
