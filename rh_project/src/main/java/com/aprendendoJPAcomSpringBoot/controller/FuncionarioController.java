package com.aprendendoJPAcomSpringBoot.controller;

import java.util.List;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.aprendendoJPAcomSpringBoot.model.*;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping("/listar")
    public String listar(Model model,
                         @RequestParam(required = false) Long departamentoId,
                         @RequestParam(required = false) Long cargoId,
                         @RequestParam(required = false) Long chefeId) {

        List<Funcionario> funcionarios;

        if (departamentoId != null) {
            funcionarios = funcionarioRepository.findByDepartamentoId(departamentoId);
        } else if (cargoId != null) {
            funcionarios = funcionarioRepository.findByCargoId(cargoId);
        } else if (chefeId != null) {
            funcionarios = funcionarioRepository.findByChefeId(chefeId);
        } else {
            funcionarios = funcionarioRepository.findAll();
        }

        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("departamentos", departamentoRepository.findAll());
        model.addAttribute("cargos", cargoRepository.findAll());
        model.addAttribute("chefes", funcionarioRepository.findAll());
        return "funcionarioListar";
    }

    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("departamentos", departamentoRepository.findAll());
        model.addAttribute("cargos", cargoRepository.findAll());
        model.addAttribute("chefes", funcionarioRepository.findAll());
        return "funcionarioCadastrar";
    }

    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String cadastrar(
            @RequestParam(required = false) Long chefeId,
            Funcionario funcionario,
            Model model) {

        String erro = null;

       
        if (funcionario.getSalario() != null && funcionario.getSalario() <= 0.0) {
            erro = "Salário deve ser positivo.";
        }

        if (funcionario.getDataContratacao() != null) {
            if (funcionario.getDataContratacao().isAfter(LocalDate.now())) {
                erro = "Data de contratação não pode ser futura.";
            }
        }

        if (funcionario.getCpf() != null) {
            Funcionario existente = funcionarioRepository.findByCpf(funcionario.getCpf());
            if (existente != null && (funcionario.getId() == null ||
                    !existente.getId().equals(funcionario.getId()))) {
                erro = "CPF já cadastrado para outro funcionário.";
            }
        }

        if (chefeId == null) {
            funcionario.setChefe(null);
        } else {
            Funcionario chefe = funcionarioRepository.findById(chefeId).orElse(null);
            funcionario.setChefe(chefe);
        }

        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("departamentos", departamentoRepository.findAll());
            model.addAttribute("cargos", cargoRepository.findAll());
            model.addAttribute("chefes", funcionarioRepository.findAll());
            model.addAttribute("funcionario", funcionario);
            return "funcionarioCadastrar";
        }

        funcionario.setId(null);
        funcionarioRepository.save(funcionario);

        return "redirect:/funcionarios/listar";
    }

    @GetMapping("/editar")
    public String editarForm(@RequestParam Long id, Model model) {
        Funcionario f = funcionarioRepository.findById(id).orElse(new Funcionario());
        model.addAttribute("funcionario", f);
        model.addAttribute("departamentos", departamentoRepository.findAll());
        model.addAttribute("cargos", cargoRepository.findAll());
        model.addAttribute("chefes", funcionarioRepository.findAll());
        return "funcionarioEditar";
    }

    @PostMapping(value = "/editar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editar(
            @RequestParam(required = false) Long chefeId,
            @RequestParam Long departamento_id,
            @RequestParam Long cargo_id,
            Funcionario funcionario,
            Model model) {

        String erro = null;

        // === VALIDAÇÕES ===
        if (funcionario.getSalario() != null && funcionario.getSalario() <= 0.0) {
            erro = "Salário deve ser positivo.";
        }

        if (funcionario.getDataContratacao() != null &&
            funcionario.getDataContratacao().isAfter(LocalDate.now())) {
            erro = "Data de contratação não pode ser futura.";
        }

        if (funcionario.getCpf() != null) {
            Funcionario existente = funcionarioRepository.findByCpf(funcionario.getCpf());
            if (existente != null && !existente.getId().equals(funcionario.getId())) {
                erro = "CPF já cadastrado para outro funcionário.";
            }
        }

        // Chefe não pode ser ele mesmo
        if (chefeId != null && chefeId.equals(funcionario.getId())) {
            erro = "Um funcionário não pode ser chefe de si mesmo.";
        }

        // Se houver erro → reexibir formulário
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("departamentos", departamentoRepository.findAll());
            model.addAttribute("cargos", cargoRepository.findAll());
            model.addAttribute("chefes", funcionarioRepository.findAll());
            model.addAttribute("funcionario", funcionario);
            return "funcionarioEditar";
        }

        Departamento dep = departamentoRepository.findById(departamento_id).orElse(null);
        Cargo cargo = cargoRepository.findById(cargo_id).orElse(null);
        Funcionario chefe = (chefeId != null) ? funcionarioRepository.findById(chefeId).orElse(null) : null;

        funcionario.setDepartamento(dep);
        funcionario.setCargo(cargo);
        funcionario.setChefe(chefe);

        funcionarioRepository.save(funcionario);

        return "redirect:/funcionarios/listar";
    }
    @GetMapping("/excluir")
    public String excluir(@RequestParam Long id, Model model) {
        try {
            funcionarioRepository.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("erro", "Operação inválida: este funcionário é chefe de alguém.");
            model.addAttribute("funcionarios", funcionarioRepository.findAll());
            model.addAttribute("departamentos", departamentoRepository.findAll());
            model.addAttribute("cargos", cargoRepository.findAll());
            model.addAttribute("chefes", funcionarioRepository.findAll());
            return "funcionarioListar";
        }
        return "redirect:/funcionarios/listar";
    }

    @GetMapping("/procurar")
    public String procurar(@RequestParam String nome, Model model) {

        List<Funcionario> resultado = funcionarioRepository.findByNomeContainingIgnoreCase(nome);

        model.addAttribute("funcionarios", resultado);
        model.addAttribute("departamentos", departamentoRepository.findAll());
        model.addAttribute("cargos", cargoRepository.findAll());
        model.addAttribute("chefes", funcionarioRepository.findAll());
        model.addAttribute("busca", nome);

        return "funcionarioListar";
    }

}
