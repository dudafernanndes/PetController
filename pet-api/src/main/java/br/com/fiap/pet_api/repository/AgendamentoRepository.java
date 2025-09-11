package br.com.fiap.pet_api.repository;

import br.com.fiap.pet_api.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Existe agendamento para esse PET exatamente nessa data e horário?
    boolean existsByPet_IdAndDataAndHorario(Long petId, LocalDate data, LocalTime horario);

    // Mesma verificação, mas ignorando um ID (útil no update)
    boolean existsByPet_IdAndDataAndHorarioAndIdNot(Long petId, LocalDate data, LocalTime horario, Long ignoreId);
}