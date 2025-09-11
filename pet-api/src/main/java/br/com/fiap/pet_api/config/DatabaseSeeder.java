package br.com.fiap.pet_api.config;

import br.com.fiap.pet_api.model.Agendamento;
import br.com.fiap.pet_api.model.Pet;
import br.com.fiap.pet_api.repository.AgendamentoRepository;
import br.com.fiap.pet_api.repository.PetRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
public class DatabaseSeeder {

    private final PetRepository petRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final Random random = new Random();

    public DatabaseSeeder(PetRepository petRepository, AgendamentoRepository agendamentoRepository) {
        this.petRepository = petRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    @PostConstruct
    public void seed() {
        // Evita duplicar seed se já houver dados (útil ao usar H2 file ou outro banco)
        if (petRepository.count() > 0 || agendamentoRepository.count() > 0) return;

        // 1) Pets base
        var pets = List.of(
            Pet.builder().nome("Thor").especie("Cachorro").raca("Labrador").idade(4).sexo("M").cor("Preto")
                    .nomeTutor("Carla").telefoneTutor("11999998888").observacoes("Vacinado").build(),
            Pet.builder().nome("Mia").especie("Gato").raca("Siames").idade(2).sexo("F").cor("Branco")
                    .nomeTutor("Bruno").telefoneTutor("11988888888").observacoes("Alergia a ração X").build(),
            Pet.builder().nome("Rex").especie("Cachorro").raca("Pastor Alemão").idade(6).sexo("M").cor("Marrom")
                    .nomeTutor("Ana").telefoneTutor("11977777777").observacoes("Treinado para guarda").build(),
            Pet.builder().nome("Luna").especie("Gato").raca("Persa").idade(3).sexo("F").cor("Cinza")
                    .nomeTutor("Diego").telefoneTutor("11966666666").observacoes("Calma").build(),
            Pet.builder().nome("Buddy").especie("Cachorro").raca("Poodle").idade(5).sexo("M").cor("Branco")
                    .nomeTutor("Larissa").telefoneTutor("11955555555").observacoes("Ansioso").build()
        );
        var savedPets = petRepository.saveAll(pets);

        // 2) Agendamentos aleatórios (futuro, horário entre 08:00 e 20:00, sem choque de horário por pet)
        var servicos = List.of("Banho", "Tosa", "Vacina", "Consulta");
        var locais = List.of("Loja A", "Loja B");
        var profissionais = List.of("João", "Ana", "Marcio", "Lívia", "Bea");

        List<Agendamento> ags = new ArrayList<>();
        Set<String> usados = new HashSet<>(); // chave: petId|data|horario para evitar duplicidade

        // Vamos criar ~20 agendamentos distribuídos
        int total = 20;
        int tentativas = 0;
        while (ags.size() < total && tentativas < total * 5) {
            tentativas++;

            Pet pet = savedPets.get(random.nextInt(savedPets.size()));
            LocalDate data = LocalDate.now().plusDays(random.nextInt(30)); // próximos 30 dias
            // slots de 30 min entre 08:00 e 20:00 (08:00..19:30 = 24 slots)
            LocalTime horario = LocalTime.of(8, 0).plusMinutes(30L * random.nextInt(24));

            String key = pet.getId() + "|" + data + "|" + horario;
            if (usados.contains(key)) continue; // evita choque de horário (e UNIQUE no banco)

            usados.add(key);
            ags.add(Agendamento.builder()
                    .pet(pet)
                    .servico(servicos.get(random.nextInt(servicos.size())))
                    .profissional(profissionais.get(random.nextInt(profissionais.size())))
                    .local(locais.get(random.nextInt(locais.size())))
                    .data(data)
                    .horario(horario)
                    .observacoes(random.nextBoolean() ? " " : "Trazer carteira de vacinação")
                    .build());
        }

        agendamentoRepository.saveAll(ags);
    }
}
