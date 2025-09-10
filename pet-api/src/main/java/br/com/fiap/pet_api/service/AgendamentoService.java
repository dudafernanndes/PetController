package br.com.fiap.pet_api.service;

import br.com.fiap.pet_api.dto.AgendamentoDTO;
import br.com.fiap.pet_api.model.Agendamento;
import br.com.fiap.pet_api.repository.AgendamentoRepository;
import br.com.fiap.pet_api.repository.PetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repo;
    private final PetRepository petRepo;

    public AgendamentoService(AgendamentoRepository repo, PetRepository petRepo) {
        this.repo = repo;
        this.petRepo = petRepo;
    }

    @Transactional
    public AgendamentoDTO create(AgendamentoDTO dto) {
        ensurePetExists(dto.idPet());
        Agendamento a = toEntity(dto);
        a.setId(null);
        return toDTO(repo.save(a));
    }

    @Transactional
    public AgendamentoDTO update(Long id, AgendamentoDTO dto) {
        ensurePetExists(dto.idPet());
        Agendamento a = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Agendamento não encontrado"));
        a.setIdPet(dto.idPet());
        a.setServico(dto.servico());
        a.setProfissional(dto.profissional());
        a.setLocal(dto.local());
        a.setData(dto.data());
        a.setHorario(dto.horario());
        a.setObservacoes(dto.observacoes());
        return toDTO(repo.save(a));
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Agendamento não encontrado");
        }
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AgendamentoDTO findById(Long id) {
        return repo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Agendamento não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoDTO> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDTO);
    }

    private void ensurePetExists(Long idPet) {
        if (idPet == null || !petRepo.existsById(idPet)) {
            throw new NoSuchElementException("Pet não encontrado para agendamento");
        }
    }

    private Agendamento toEntity(AgendamentoDTO dto) {
        return Agendamento.builder()
                .id(dto.id())
                .idPet(dto.idPet())
                .servico(dto.servico())
                .profissional(dto.profissional())
                .local(dto.local())
                .data(dto.data())
                .horario(dto.horario())
                .observacoes(dto.observacoes())
                .build();
    }

    private AgendamentoDTO toDTO(Agendamento a) {
        return new AgendamentoDTO(
                a.getId(),
                a.getIdPet(),
                a.getServico(),
                a.getProfissional(),
                a.getLocal(),
                a.getData(),
                a.getHorario(),
                a.getObservacoes()
        );
    }
}
