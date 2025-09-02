package br.com.fiap.pet_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "TB_AGENDAMENTO")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idPet; // (poderia ser @ManyToOne se quiser)

    @Column(length=40, nullable=false)
    private String servico;

    @Column(length=60)
    private String profissional;

    @Column(length=60)
    private String local;

    private LocalDate data;
    private LocalTime horario;

    @Column(length=255)
    private String observacoes;
}
