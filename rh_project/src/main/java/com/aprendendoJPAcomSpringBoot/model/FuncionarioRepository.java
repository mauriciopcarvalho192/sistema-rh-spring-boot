package com.aprendendoJPAcomSpringBoot.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByDepartamentoId(Long departamentoId);
    List<Funcionario> findByCargoId(Long cargoId);
    List<Funcionario> findByChefeId(Long chefeId);
    Funcionario findByCpf(String cpf);
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);

}
