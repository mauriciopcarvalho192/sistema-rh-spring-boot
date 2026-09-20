Projeto RH - Esqueleto gerado pelo assistente
=============================================

O projeto contém um esqueleto Spring Boot (Maven) com as entidades:
- Cargo
- Departamento
- Funcionario

Controllers, repositórios e templates Thymeleaf básicos foram incluidos,
seguindo o estilo do projeto de referência fornecido.

Como usar:
1. Coloque o MySQL do XAMPP rodando (porta padrão 3306).
2. Crie um banco (ex: rh_spring) ou use a configuração padrão (rh_spring).
3. Ajuste username/password em src/main/resources/application.properties se necessário.
4. No terminal, dentro da pasta do projeto:
   mvn clean package
   mvn spring-boot:run
5. Acesse:
   http://localhost:8080/funcionarios/listar
   http://localhost:8080/cargos/listar
   http://localhost:8080/departamentos/listar

Observações:
- O projeto é um ponto de partida. Ajustes finos, validações adicionais e testes devem ser aplicados.
- Se quiser que eu altere a organização, nomes, ou inclua exemplos de dados iniciais, diga qual mudança prefere.
