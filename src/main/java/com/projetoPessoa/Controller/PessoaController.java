package com.projetoPessoa.Controller;

import com.projetoPessoa.model.Pessoa;
import com.projetoPessoa.repository.PessoaRepository;
import com.projetoPessoa.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository repository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pessoas", repository.findAll());
        return "listar";
    }

    @GetMapping("/buscar")
    public String mostrarPaginaDeBusca() {
        return "buscar"; // vai abrir o buscar.html
    }

    @GetMapping("/buscar/resultado")
    public String buscarPorCpf(@RequestParam String cpf, Model model) {
        Pessoa pessoa = repository.findByCpf(cpf);

        if (pessoa != null) {
            model.addAttribute("pessoa", pessoa);
            return "resultado"; // vai abrir o resultado.html
        } else {
            model.addAttribute("mensagem", "Nenhuma pessoa encontrada com esse CPF!");
            return "buscar";
        }
    }




    @GetMapping("/nova")
    public String novaPessoaForm(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        return "form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Pessoa pessoa) {
        repository.save(pessoa);
        return "redirect:/pessoas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("pessoa", repository.findById(id).get());
        return "form";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/pessoas";
    }
}
