package br.com.fiap.pet_api.repository;

import br.com.fiap.pet_api.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> { }
