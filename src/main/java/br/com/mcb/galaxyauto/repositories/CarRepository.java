package br.com.mcb.galaxyauto.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mcb.galaxyauto.entities.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, UUID> {

}
