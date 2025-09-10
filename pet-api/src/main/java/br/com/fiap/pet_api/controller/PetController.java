package br.com.fiap.pet_api.controller;

import br.com.fiap.pet_api.dto.PetDTO;
import br.com.fiap.pet_api.service.PetService;
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
@RequestMapping("/pets")
public class PetController {

    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    // CREATE (um por vez)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDTO create(@RequestBody @Valid PetDTO dto) {
        return service.create(dto);
    }

    // CREATE em lote
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public List<PetDTO> createBatch(@RequestBody @Valid List<PetDTO> dtos) {
        return dtos.stream().map(service::create).toList();
    }

    // UPDATE
    @PutMapping("/{id}")
    public PetDTO update(@PathVariable @Positive Long id,
                         @RequestBody @Valid PetDTO dto) {
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
    public PetDTO findById(@PathVariable @Positive Long id) {
        return service.findById(id);
    }

    // LISTAR só a lista (sem metadados de página)
    @GetMapping
    public List<PetDTO> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return service.findAll(pageable).getContent();
    }
}
