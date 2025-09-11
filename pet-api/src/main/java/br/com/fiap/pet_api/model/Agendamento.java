package br.com.fiap.pet_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
    name = "TB_AGENDAMENTO",
    uniqueConstraints = @UniqueConstraint(
        name = "UK_AG_PET_DATA_HORA",
        columnNames = {"id_pet", "data", "horario"}
    ),
    indexes = {
        @Index(name = "IDX_AG_PET", columnList = "id_pet"),
        @Index(name = "IDX_AG_DATA", columnList = "data")
    }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELACIONAMENTO: muitos agendamentos para um pet
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pet", nullable = false,
            foreignKey = @ForeignKey(name = "FK_AGENDAMENTO_PET"))
    private Pet pet;

    @Column(length = 40, nullable = false)
    private String servico;

    @Column(length = 60)
    private String profissional;

    @Column(length = 60)
    private String local;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @Column(length = 255)
    private String observacoes;
}
