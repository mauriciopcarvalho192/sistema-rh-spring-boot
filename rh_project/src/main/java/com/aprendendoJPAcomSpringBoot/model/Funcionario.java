package com.aprendendoJPAcomSpringBoot.model;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @Column(unique = true)
    private String cpf;

    private String email;

    private LocalDate dataContratacao;


    private Double salario;

    @ManyToOne
    private Departamento departamento;

    @ManyToOne
    private Cargo cargo;

    @ManyToOne
    private Funcionario chefe;

    public Funcionario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }

    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }

    public Funcionario getChefe() { return chefe; }
    public void setChefe(Funcionario chefe) { this.chefe = chefe; }

    @Override
    public String toString() {
        return "Funcionario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", dataContratacao="
                + dataContratacao + ", salario=" + salario + ", departamento=" + (departamento != null ? departamento.getId() : null)
                + ", cargo=" + (cargo != null ? cargo.getId() : null) + ", chefe=" + (chefe != null ? chefe.getId() : null) + "]";
    }
}
