# Sistema de Gestão de Recursos Humanos

Sistema web de gestão de recursos humanos desenvolvido como projeto acadêmico, utilizando Java e Spring Boot.

## Sobre o projeto

O sistema tem como objetivo oferecer funcionalidades para o gerenciamento de informações de funcionários, utilizando uma aplicação web integrada a um banco de dados relacional.

## Funcionalidades

* Cadastro de funcionários
* Listagem de funcionários
* Edição de registros
* Exclusão de registros
* Busca e filtragem de informações
* Persistência de dados em banco de dados
* Validações e relacionamentos entre entidades

## Tecnologias utilizadas

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Thymeleaf
* HTML
* Maven

## Arquitetura e conceitos

O projeto utiliza conceitos de:

* Programação Orientada a Objetos
* Desenvolvimento de aplicações web
* Persistência de dados com JPA
* Mapeamento objeto-relacional (ORM)
* Operações CRUD
* Integração com banco de dados relacionais

## Como executar

### Pré-requisitos

* Java instalado
* Maven
* MySQL

### Configuração

1. Coloque o MySQL do XAMPP rodando (porta padrão 3306).
2. Crie um banco (ex: rh_spring) ou use a configuração padrão (rh_spring).
3. Ajuste username/password em src/main/resources/application.properties se necessário.
4. No terminal, dentro da pasta do projeto: mvn clean package mvn spring-boot:run
5. Acesse: http://localhost:8080/funcionarios/listar http://localhost:8080/cargos/listar http://localhost:8080/departamentos/listar

### Execução

A aplicação pode ser executada pela classe principal `RhSystemApplication`.

## Observações

Projeto desenvolvido para fins acadêmicos no curso de Tecnologia em Análise e Desenvolvimento de Sistemas.
