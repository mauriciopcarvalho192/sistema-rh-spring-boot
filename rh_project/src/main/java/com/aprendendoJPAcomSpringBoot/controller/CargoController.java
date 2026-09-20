package com.aprendendoJPAcomSpringBoot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.aprendendoJPAcomSpringBoot.model.Cargo;
import com.aprendendoJPAcomSpringBoot.model.CargoRepository;

@Controller
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("cargos", cargoRepository.findAll());
        return "cargoListar";
    }

    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("cargo", new Cargo());
        return "cargoCadastrar";
    }

    @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String cadastrar(Cargo cargo, Model model) {
        cargo.setId(null);
        cargoRepository.save(cargo);
        return "redirect:/cargos/listar";
    }

    @GetMapping("/editar")
    public String editarForm(@RequestParam Long id, Model model) {
        model.addAttribute("cargo", cargoRepository.findById(id).orElse(new Cargo()));
        return "cargoEditar";
    }

    @PostMapping(value = "/editar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editar(Cargo cargo, Model model) {
        cargoRepository.save(cargo);
        return "redirect:/cargos/listar";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam Long id, Model model) {
        try {
            cargoRepository.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("erro", "Operação inválida: este cargo está em uso.");
            model.addAttribute("cargos", cargoRepository.findAll());
            return "cargoListar"; 
        }
        return "redirect:/cargos/listar";
    }
    @GetMapping("/procurar")
    public String procurar(@RequestParam String nome, Model model) {

        List<Cargo> cargos = cargoRepository.findByNomeContainingIgnoreCase(nome);

        model.addAttribute("cargos", cargos);
        model.addAttribute("busca", nome);

        return "cargoListar";
    }


}
