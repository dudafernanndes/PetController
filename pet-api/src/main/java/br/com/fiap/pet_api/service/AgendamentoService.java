package br.com.fiap.pet_api.service;

import br.com.fiap.pet_api.dto.AgendamentoDTO;
import br.com.fiap.pet_api.model.Agendamento;
import br.com.fiap.pet_api.model.Pet;
import br.com.fiap.pet_api.repository.AgendamentoRepository;
import br.com.fiap.pet_api.repository.PetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
        Pet pet = fetchPet(dto.idPet());
        ensureSlotFree(pet.getId(), dto.data(), dto.horario(), null);
        Agendamento a = toEntity(dto, pet);
        a.setId(null);
        return toDTO(repo.save(a));
    }

    @Transactional
    public List<AgendamentoDTO> createBatch(List<AgendamentoDTO> dtos) {
        // valida tudo antes de salvar (fail-fast com mensagem do item problemático, se preferir)
        for (AgendamentoDTO dto : dtos) {
            Pet p = fetchPet(dto.idPet());
            ensureSlotFree(p.getId(), dto.data(), dto.horario(), null);
        }
        List<Agendamento> entities = dtos.stream()
                .map(dto -> toEntity(dto, fetchPet(dto.idPet())))
                .toList();

        List<Agendamento> saved = repo.saveAll(entities);
        return saved.stream().map(this::toDTO).toList();
    }

    @Transactional
    public AgendamentoDTO update(Long id, AgendamentoDTO dto) {
        Agendamento a = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Agendamento não encontrado"));

        Pet pet = fetchPet(dto.idPet());
        ensureSlotFree(pet.getId(), dto.data(), dto.horario(), id);

        a.setPet(pet);
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

    // ---------- helpers ----------

    private Pet fetchPet(Long idPet) {
        if (idPet == null) throw new NoSuchElementException("Pet não encontrado para agendamento");
        return petRepo.findById(idPet)
                .orElseThrow(() -> new NoSuchElementException("Pet não encontrado para agendamento"));
    }

    private void ensureSlotFree(Long petId, LocalDate data, LocalTime horario, Long ignoreId) {
        boolean ocupado = (ignoreId == null)
                ? repo.existsByPet_IdAndDataAndHorario(petId, data, horario)
                : repo.existsByPet_IdAndDataAndHorarioAndIdNot(petId, data, horario, ignoreId);

        if (ocupado) {
            // Se tiver o handler mapeando IllegalStateException para 409, melhor UX
            throw new IllegalStateException("Esse pet já possui agendamento nesse dia e horário.");
        }
    }

    private Agendamento toEntity(AgendamentoDTO dto, Pet pet) {
        return Agendamento.builder()
                .id(dto.id())
                .pet(pet)
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
                a.getPet() != null ? a.getPet().getId() : null,
                a.getServico(),
                a.getProfissional(),
                a.getLocal(),
                a.getData(),
                a.getHorario(),
                a.getObservacoes()
        );
    }
}
