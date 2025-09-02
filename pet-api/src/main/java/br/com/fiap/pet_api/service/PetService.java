package br.com.fiap.pet_api.service;

import br.com.fiap.pet_api.dto.PetDTO;
import br.com.fiap.pet_api.model.Pet;
import br.com.fiap.pet_api.repository.PetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PetService {

    private final PetRepository repo;

    public PetService(PetRepository repo) { this.repo = repo; }

    public PetDTO create(PetDTO dto) {
        Pet p = toEntity(dto);
        p.setId(null);
        return toDTO(repo.save(p));
    }

    public PetDTO update(Long id, PetDTO dto) {
        Pet p = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Pet não encontrado"));
        p.setNome(dto.nome());
        p.setEspecie(dto.especie());
        p.setRaca(dto.raca());
        p.setIdade(dto.idade());
        p.setSexo(dto.sexo());
        p.setCor(dto.cor());
        p.setNomeTutor(dto.nomeTutor());
        p.setTelefoneTutor(dto.telefoneTutor());
        p.setObservacoes(dto.observacoes());
        return toDTO(repo.save(p));
    }

    public void delete(Long id) { repo.deleteById(id); }

    public PetDTO findById(Long id) {
        return repo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Pet não encontrado"));
    }

    public Page<PetDTO> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDTO);
    }

    private Pet toEntity(PetDTO dto) {
        return Pet.builder()
                .id(dto.id()).nome(dto.nome()).especie(dto.especie()).raca(dto.raca())
                .idade(dto.idade()).sexo(dto.sexo()).cor(dto.cor())
                .nomeTutor(dto.nomeTutor()).telefoneTutor(dto.telefoneTutor())
                .observacoes(dto.observacoes()).build();
    }

    private PetDTO toDTO(Pet p) {
        return new PetDTO(
                p.getId(), p.getNome(), p.getEspecie(), p.getRaca(), p.getIdade(),
                p.getSexo(), p.getCor(), p.getNomeTutor(), p.getTelefoneTutor(), p.getObservacoes()
        );
    }
}
