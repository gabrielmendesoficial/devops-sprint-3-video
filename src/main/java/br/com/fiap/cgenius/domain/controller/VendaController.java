package br.com.fiap.cgenius.domain.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.cgenius.domain.model.Venda;
import br.com.fiap.cgenius.domain.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/venda")
@Slf4j
@Tag(name = "vendas", description = "Endpoint relacionado com vendas")
public class VendaController {
    @Autowired
    VendaService vendaService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Lista todas as vendas cadastrados no sistema.", description = "Endpoint que retorna um array de objetos do tipo venda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de vendas retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
})
    public List<Venda> index(){
        return vendaService.findAll();
    }

    
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Cria uma nova venda.", description = "Endpoint que cria uma nova venda no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação de venda"),
            @ApiResponse(responseCode = "201", description = "venda cadastrada com sucesso")
    })
    public Venda create(@RequestBody Venda venda){
        log.info("cadastrando venda: {}", venda);
        return  vendaService.create(venda);
    }

    @GetMapping("{id}")
    @Operation(summary = "Retorna um venda especifico cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo venda com um id informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda encontrada"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada")
})
    public ResponseEntity<Venda> get(@PathVariable Long id){
        log.info("Buscar por id: {}", id);
        Venda venda = vendaService.findById(id);
        if (venda != null) {
            return ResponseEntity.ok(venda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("cpfatendente/{cpf_atendente}")
    @Operation(summary = "Retorna uma venda especifico cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo venda com um cpf de atendente informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
})
    public ResponseEntity<List<Venda>> getByAtendente(@PathVariable String cpf_atendente){
        log.info("Buscar por CPF: {}", cpf_atendente);
        List<Venda> venda = vendaService.findByCpfAtendente(cpf_atendente);
    if (venda != null) {
        return ResponseEntity.ok(venda);
    } else {
        return ResponseEntity.notFound().build();
    }
    }

    @GetMapping("cpfcliente/{cpf_cliente}")
    @Operation(summary = "Retorna um venda especifico cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo venda com um cpf de cliente informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    
    public ResponseEntity<List<Venda>> getByCliente(@PathVariable String cpf_cliente){
        log.info("Buscar por CPF: {}", cpf_cliente);
        List<Venda> venda = vendaService.findByCpfCliente(cpf_cliente);
    if (venda != null) {
        return ResponseEntity.ok(venda);
    } else {
        return ResponseEntity.notFound().build();
    }
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Remove uma venda do sistema.", description = "Endpoint que remove uma venda do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venda removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada")
    })
    public void destroy (@PathVariable Long id){
        log.info("Apagando id {}", id);
        vendaService.delete(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza uma venda existente.", description = "Endpoint que atualiza uma venda existente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada")
    })
    public Venda update(@PathVariable Long id, @RequestBody Venda venda){
        log.info("Atualizando o cadastro do id={} para {}", id, venda);
        return vendaService.update(id, venda);
    }
}
