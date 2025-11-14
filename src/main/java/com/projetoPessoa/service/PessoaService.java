package com.projetoPessoa.service;

import com.projetoPessoa.model.Pessoa;
import com.projetoPessoa.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PessoaService {
    private final PessoaRepository repository;
    private final LinkedList <Pessoa> listaLigada = new LinkedList<>();

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> listarTodas() {
        List<Pessoa> pessoas = repository.findAll();
        listaLigada.clear();
        listaLigada.addAll(pessoas);
        return pessoas;
    }
    public Pessoa criar (Pessoa pessoa){
        return repository.save(pessoa);
    }

    public Pessoa editar (Long id, Pessoa novaPessoa) {
        Pessoa pessoaEditar = repository.findById(id).orElse(null);
        pessoaEditar.setNome(novaPessoa.getNome());
        pessoaEditar.setCpf(novaPessoa.getCpf());
        pessoaEditar.setEndereco(novaPessoa.getEndereco());
        Pessoa pessoaAtualizar = repository.save(pessoaEditar);

        for (int i = 0; i < listaLigada.size(); i++) {
            if (listaLigada.get(i).getId().equals(id)) {
                listaLigada.set(i, pessoaAtualizar);
                break;
            }
        }
        return pessoaAtualizar;
    }

    public void deletar (Long id){
        repository.deleteById(id);
        listaLigada.removeIf(p -> p.getId().equals(id));
    }
    public LinkedList <Pessoa> getListaLigada() {
        return listaLigada;
    }
}
