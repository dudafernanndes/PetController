package br.com.fiap.pet_api.controller;

import br.com.fiap.pet_api.dto.AgendamentoDTO;
import br.com.fiap.pet_api.service.AgendamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    // CREATE (um por vez)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendamentoDTO create(@RequestBody @Valid AgendamentoDTO dto) {
        return service.create(dto);
    }

    // CREATE em lote (transacional no service)
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AgendamentoDTO> createBatch(@RequestBody @Valid List<AgendamentoDTO> dtos) {
        return service.createBatch(dtos);
    }

    // UPDATE
    @PutMapping("/{id}")
    public AgendamentoDTO update(@PathVariable @Positive Long id,
                                 @RequestBody @Valid AgendamentoDTO dto) {
        return service.update(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive Long id) {
        service.delete(id);
    }

    // GET por ID
    @GetMapping("/{id}")
    public AgendamentoDTO findById(@PathVariable @Positive Long id) {
        return service.findById(id);
    }

    // LISTAR só a lista (sem metadados da página)
    @GetMapping
    public List<AgendamentoDTO> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return service.findAll(pageable).getContent();
    }
}
