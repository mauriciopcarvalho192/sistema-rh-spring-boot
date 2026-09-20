package com.aprendendoJPAcomSpringBoot.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
	List<Cargo> findByNomeContainingIgnoreCase(String nome);

}
