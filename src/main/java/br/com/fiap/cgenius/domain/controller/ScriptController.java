package br.com.fiap.cgenius.domain.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import br.com.fiap.cgenius.domain.model.Script;
import br.com.fiap.cgenius.domain.service.ScriptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RequestMapping("/script")
@Slf4j
@Tag(name = "scripts", description = "Endpoint relacionado com scripts")
public class ScriptController {
    @Autowired
    ScriptService scriptService;


    @GetMapping
    @Operation(summary = "Lista todos os scripts cadastrados no sistema.", description = "Endpoint que retorna um array de objetos do tipo script")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de scripts retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<Script> index(){
        return scriptService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Cria um novo script.", description = "Endpoint para criar um novo script")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Script cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do script"),
        @ApiResponse(responseCode = "404", description = "Histórico, chamada ou cliente não encontrados"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Script create(@RequestBody Script script){
        log.info("cadastrando script: {}", script);
        return scriptService.create(script);
    }

    @GetMapping("{id}")
    @Operation(summary = "Retorna um script especifico cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo script com um id informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Script encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Script não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Script> get(@PathVariable Long id){
        log.info("Buscar por id: {}", id);
        Script script = scriptService.findById(id);
        if(script != null){
            return ResponseEntity.ok(script);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deleta um script pelo ID.", description = "Endpoint que deleta um script com um ID informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Script deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Script não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public void destroy (@PathVariable Long id){
        log.info("Apagando id {}", id);
        scriptService.delete(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza um script pelo ID.", description = "Endpoint que atualiza um script com um ID informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Script atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do script"),
        @ApiResponse(responseCode = "404", description = "Script não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public  Script update(@PathVariable Long id, @RequestBody Script script){
        log.info("Atualizando o cadastro do id={} para {}", id, script);
        script.setId(id);
        return scriptService.update(id, script);
    }
}
