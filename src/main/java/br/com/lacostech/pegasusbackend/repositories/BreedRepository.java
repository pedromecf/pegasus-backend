package br.com.lacostech.pegasusbackend.repositories;

import br.com.lacostech.pegasusbackend.model.entities.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {
}