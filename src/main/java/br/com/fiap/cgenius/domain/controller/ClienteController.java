package br.com.fiap.cgenius.domain.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.cgenius.domain.dto.ClienteRequest;
import br.com.fiap.cgenius.domain.dto.ClienteResponse;
import br.com.fiap.cgenius.domain.dto.ClienteUpdate;
import br.com.fiap.cgenius.domain.model.Cliente;
import br.com.fiap.cgenius.domain.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RequestMapping("/cliente")
@Slf4j
@CacheConfig(cacheNames = "clientes")
@Tag(name = "clientes", description = "Endpoint relacionado com clientes")
public class ClienteController {
    
    @Autowired
    ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Lista todos os clientes cadastrados no sistema.", description = "Endpoint que retorna um array de objetos do tipo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Lista todos os clientes cadastrados no sistema.", description = "Endpoint que retorna um array de objetos do tipo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do cliente"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ClienteResponse> create(@RequestBody ClienteRequest clienteRequest, UriComponentsBuilder uriBuilder){
        log.info("cadastrando cliente: {}", clienteRequest);
        var cliente = clienteService.create(clienteRequest.toModel());
        var uri = uriBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(ClienteResponse.from(cliente)); 
    }

    @GetMapping("{id}")
    @Operation(summary = "Retorna um cliente especifico cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo cliente com um id informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ClienteResponse> get(@PathVariable Long id){
        log.info("Buscar por id: {}", id);
        Cliente cliente = clienteService.findById(id);
        if (cliente != null) {
            return ResponseEntity.ok(ClienteResponse.from(cliente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("cpf/{cpf_cliente}")
    @Operation(summary = "Retorna um cliente especifico cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo cliente com um cpf informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ClienteResponse> get(@PathVariable String cpf_cliente){
        log.info("Buscar por CPF: {}", cpf_cliente);
        Cliente cliente = clienteService.findByCpf(cpf_cliente);
    if (cliente != null) {
        return ResponseEntity.ok(ClienteResponse.from(cliente));
    } else {
        return ResponseEntity.notFound().build();
    }
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deleta um cliente pelo ID.", description = "Endpoint que deleta um cliente com um ID informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public void destroy (@PathVariable Long id){
        log.info("Apagando id {}", id);
        clienteService.deleteById(id);
    }

    @Transactional
    @DeleteMapping("cpf/{cpf_cliente}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deleta um cliente pelo CPF.", description = "Endpoint que deleta um cliente com um CPF informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public void deleteByCpf_cliente (@PathVariable String cpf_cliente){
        log.info("Apagando Cliente com CPF {}", cpf_cliente);
        clienteService.deleteByCpf(cpf_cliente);
    }

    @Transactional
    @PutMapping("{id}")
    @Operation(summary = "Atualiza um cliente pelo ID.", description = "Endpoint que atualiza um cliente com um ID informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do cliente"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ClienteResponse> update(@PathVariable Long id, @RequestBody ClienteUpdate clienteUpdate){
        log.info("Atualizando o cadastro do id = {} para {}", id, clienteUpdate);
        Cliente cliente = clienteService.findById(id);
        clienteUpdate.toModel(cliente);
        Cliente c = clienteService.update(cliente);
        return ResponseEntity.ok(ClienteResponse.from(c));
    }

    @PutMapping("cpf/{cpf_cliente}")
    @Operation(summary = "Atualiza um cliente pelo CPF.", description = "Endpoint que atualiza um cliente com um CPF informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do cliente"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<ClienteResponse> update(@PathVariable String cpf_cliente, @RequestBody ClienteUpdate clienteUpdate){
        log.info("Atualizando o cadastro do cpf={} para {}", cpf_cliente, clienteUpdate);
        Cliente cliente = clienteService.findByCpf(cpf_cliente);
        clienteUpdate.toModel(cliente);
        return ResponseEntity.ok(ClienteResponse.from(clienteService.update(cliente)));
    }
}
