package com.aprendendoJPAcomSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.aprendendoJPAcomSpringBoot.model.Departamento;
import com.aprendendoJPAcomSpringBoot.model.DepartamentoRepository;
import java.util.List;
@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("departamentos", departamentoRepository.findAll());
        return "departamentoListar";
    }

    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("departamento", new Departamento());
        return "departamentoCadastrar";
    }

    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String cadastrar(Departamento departamento, Model model) {
        departamento.setId(null);
        departamentoRepository.save(departamento);
        return "redirect:/departamentos/listar";
    }

    @GetMapping("/editar")
    public String editarForm(@RequestParam Long id, Model model) {
        model.addAttribute("departamento", departamentoRepository.findById(id).orElse(new Departamento()));
        return "departamentoEditar";
    }

    @PostMapping(value = "/editar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editar(Departamento departamento, Model model) {
        departamentoRepository.save(departamento);
        return "redirect:/departamentos/listar";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam Long id, Model model) {
        try {
            departamentoRepository.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("erro", "Operação inválida: este departamento está em uso.");
            model.addAttribute("departamentos", departamentoRepository.findAll());
            return "departamentoListar";
        }
        return "redirect:/departamentos/listar";
    }

    @GetMapping("/procurar")
    public String procurar(@RequestParam String nome, Model model) {

        List<Departamento> deps = departamentoRepository.findByNomeContainingIgnoreCase(nome);

        model.addAttribute("departamentos", deps);
        model.addAttribute("busca", nome);

        return "departamentoListar";
    }

}
