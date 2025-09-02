package br.com.fiap.pet_api.repository;

import br.com.fiap.pet_api.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> { }
