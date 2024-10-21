package br.com.mcb.galaxyauto.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mcb.galaxyauto.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>{

}
