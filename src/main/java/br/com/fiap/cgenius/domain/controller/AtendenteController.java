package br.com.fiap.cgenius.domain.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import br.com.fiap.cgenius.domain.dto.AtendenteRequest;
import br.com.fiap.cgenius.domain.dto.AtendenteResponse;
import br.com.fiap.cgenius.domain.dto.AtendenteUpdate;
import br.com.fiap.cgenius.domain.model.Atendente;
import br.com.fiap.cgenius.domain.service.AtendenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RequestMapping("/atendente")
@Slf4j
@CacheConfig(cacheNames = "atendentes")
@Tag(name = "atendentes", description = "Endpoint relacionado com atendentes")
public class AtendenteController {
    @Autowired
    AtendenteService atendenteService;

    @GetMapping
    @Cacheable
    @Operation(summary = "Lista todos os atendentes cadastrados no sistema.", description = "Endpoint que retorna um array de objetos do tipo atendente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de atendentes retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
})
    public List<Atendente> index(){
        log.info("Listando todos os atendentes");
        return atendenteService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Cria um novo atendente.", description = "Endpoint para criar um novo atendente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Atendente cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do atendente"),
        @ApiResponse(responseCode = "409", description = "Atendente já cadastrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
})
    public ResponseEntity<AtendenteResponse> create(@RequestBody AtendenteRequest atendenteRequest, UriComponentsBuilder UriBuilder){
        log.info("cadastrando cliente: {}", atendenteRequest);
        var atendente = atendenteService.create(atendenteRequest.toModel());
        var uri = UriBuilder.path("/atendente/{id}").buildAndExpand(atendente.getId()).toUri();
        return ResponseEntity.created(uri).body(AtendenteResponse.from(atendente));
}

    @GetMapping("{id}")
    @Operation(summary = "Retorna um atendente especifico cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo atendente com um id informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Atendente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Atendente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
})
    public ResponseEntity<AtendenteResponse> get(@PathVariable Long id){
        log.info("Buscar por id: {}", id);
        Atendente atendente = atendenteService.findById(id);
        if (atendente != null) {
            return ResponseEntity.ok(AtendenteResponse.from(atendente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("cpf/{cpf_atendente}")
    @Operation(summary = "Retorna um atendente especifico cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo atendente com um cpf informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Atendente encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Atendente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
})
    public ResponseEntity<AtendenteResponse> get(@PathVariable String cpf_atendente){
        log.info("Buscar por CPF: {}", cpf_atendente);
        Atendente atendente = atendenteService.findByCpf(cpf_atendente);
    if (atendente != null) {
        return ResponseEntity.ok(AtendenteResponse.from(atendente));
    } else {
        return ResponseEntity.notFound().build();
    }
    }
    
    
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deleta um atendente pelo ID.", description = "Endpoint que deleta um atendente com um ID informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Atendente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Atendente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
})
    public void destroy (@PathVariable Long id){
        log.info("Apagando id {}", id);
        atendenteService.delete(id);
    }
    
    @Transactional
    @DeleteMapping("cpf/{cpf_atendente}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Operation(summary = "Deleta um atendente pelo CPF.", description = "Endpoint que deleta um atendente com um CPF informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Atendente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Atendente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
})
    public void deleteByCpf_atendente (@PathVariable String cpf_atendente){
        log.info("Apagando Atendente com CPF {}", cpf_atendente);
        atendenteService.deleteByCpf(cpf_atendente);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualiza um atendente pelo ID.", description = "Endpoint que atualiza um atendente com um ID informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Atendente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Atendente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
})
    public ResponseEntity<AtendenteResponse> update(@PathVariable Long id, @RequestBody AtendenteUpdate atendenteUpdate) {
        log.info("Atualizando o cadastro do id={} para {}", id, atendenteUpdate);
        Atendente atendente = atendenteService.findById(id);
        atendenteUpdate.toModel(atendente);
        Atendente a = atendenteService.update(atendente);
        return ResponseEntity.ok(AtendenteResponse.from(a));
    }

    @PutMapping("cpf/{cpf_atendente}")
    @CacheEvict(allEntries = true)
    @Operation(summary = "Atualiza um atendente pelo CPF.", description = "Endpoint que atualiza um atendente com um CPF informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Atendente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Atendente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
})
    public ResponseEntity<AtendenteResponse> update(@PathVariable String cpf_atendente, @RequestBody AtendenteUpdate atendenteUpdate){
        log.info("Atualizando o cadastro do id={} para {}", cpf_atendente, atendenteUpdate);
        Atendente atendente = atendenteService.findByCpf(cpf_atendente);
        atendenteUpdate.toModel(atendente);
        return ResponseEntity.ok(AtendenteResponse.from(atendenteService.update(atendente)));
    }
}
