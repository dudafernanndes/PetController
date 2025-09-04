package br.com.fiap.pet_api.controller;

import br.com.fiap.pet_api.dto.AgendamentoDTO;
import br.com.fiap.pet_api.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendamentoDTO create(@RequestBody @Valid AgendamentoDTO dto) {
        return service.create(dto);
    }

    // UPDATE
    @PutMapping("/{id}")
    public AgendamentoDTO update(@PathVariable Long id, @RequestBody @Valid AgendamentoDTO dto) {
        return service.update(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // GET por ID (necessário para evitar 405 em /agendamentos/{id})
    @GetMapping("/{id}")
    public AgendamentoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    // LISTAR apenas a lista (sem metadados de paginação)
    @GetMapping
    public List<AgendamentoDTO> findAll(
            @org.springframework.data.web.PageableDefault(size = 10, sort = "id",
                    direction = org.springframework.data.domain.Sort.Direction.ASC)
            org.springframework.data.domain.Pageable pageable) {
        return service.findAll(pageable).getContent();
    }
}
