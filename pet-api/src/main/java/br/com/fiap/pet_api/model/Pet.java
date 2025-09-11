package br.com.fiap.pet_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_PET")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=80, nullable=false)
    private String nome;

    @Column(length=40, nullable=false)
    private String especie;

    @Column(length=60)
    private String raca;

    private Integer idade;

    @Column(length=10)
    private String sexo;

    @Column(length=30)
    private String cor;

    @Column(length=80, nullable=false)
    private String nomeTutor;

    @Column(length=20, nullable=false)
    private String telefoneTutor;

    @Column(length=255)
    private String observacoes;

    // RELACIONAMENTO inverso (opcional para navegação)
    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Agendamento> agendamentos = new ArrayList<>();
}
