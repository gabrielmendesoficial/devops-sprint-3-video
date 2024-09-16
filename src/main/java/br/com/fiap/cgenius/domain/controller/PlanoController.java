package br.com.fiap.cgenius.domain.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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

import br.com.fiap.cgenius.domain.model.Plano;
import br.com.fiap.cgenius.domain.service.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RequestMapping("/plano")
@Slf4j
@CacheConfig(cacheNames = "planos")
@Tag(name = "planos", description = "Endpoint relacionado com planos")
public class PlanoController {
    @Autowired
    PlanoService planoService;

    @GetMapping
    @Operation(summary = "Lista todos os planos cadastrados no sistema.", description = "Endpoint que retorna um array de objetos do tipo plano")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de planos retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<Plano> index(){
        return planoService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Lista todos os Planos cadastrados no sistema.", description = "Endpoint que retorna um array de objetos do tipo Plano")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Plano cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do Plano"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Plano create(@RequestBody Plano plano){
        log.info("cadastrando plano: {}", plano);
        return planoService.create(plano);
    }

    @GetMapping("{id}")
    @Operation(summary = "Retorna um plano especifico cadastrado no sistema.", description = "Endpoint que retorna um objeto do tipo plano com um id informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plano encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Plano não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Plano> get(@PathVariable Long id){
        log.info("Buscar por id: {}", id);
        Plano plano = planoService.findById(id);
        if (plano != null) {
            return ResponseEntity.ok(plano);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Deleta um plano pelo ID.", description = "Endpoint que deleta um plano com um ID informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Plano deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Plano não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public void destroy (@PathVariable Long id){
        log.info("Apagando id {}", id);
        planoService.delete(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza um cliente pelo ID.", description = "Endpoint que atualiza um cliente com um ID informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação do cliente"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Plano update(@PathVariable Long id, @RequestBody Plano plano){
        log.info("Atualizando o cadastro do id={} para {}", id, plano);
        return planoService.update(id, plano);
    }
}
