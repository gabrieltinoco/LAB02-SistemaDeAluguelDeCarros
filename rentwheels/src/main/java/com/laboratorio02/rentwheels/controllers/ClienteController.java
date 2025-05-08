package com.laboratorio02.rentwheels.controllers;

import com.laboratorio02.rentwheels.dto.RequisicaoFormCliente;
import com.laboratorio02.rentwheels.models.Cliente;
import com.laboratorio02.rentwheels.repositories.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("")
    public ModelAndView index() {
        List<Cliente> clientes = clienteRepository.findAll();

        ModelAndView mv = new ModelAndView("clientes/index");
        mv.addObject("clientes", clientes);
        mv.addObject("page", "clientes");
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("clientes/novo");
        mv.addObject("requisicaoFormCliente", new RequisicaoFormCliente());
        mv.addObject("page", "clientes/novo");
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoFormCliente requisicao, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("clientes/novo");
            mv.addObject("page", "clientes/novo");
            return mv;
        } else {
            Optional<Cliente> existingCliente = clienteRepository.findByEmail(requisicao.getEmail());

            if (existingCliente.isPresent()) {
                bindingResult.rejectValue("email", "error.requisicao", "Este e-mail já está em uso.");
                ModelAndView mv = new ModelAndView("clientes/novo");
                mv.addObject("page", "clientes/novo");
                return mv;
            }

            Cliente cliente = requisicao.toCliente();
            this.clienteRepository.save(cliente);
            return new ModelAndView("redirect:/clientes");
        }
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {

        Optional<Cliente> optional = this.clienteRepository.findById(id);

        if (optional.isPresent()) {
            Cliente cliente = optional.get();
            ModelAndView mv = new ModelAndView("clientes/show");
            mv.addObject("page", "clientes/show");
            mv.addObject("cliente", cliente);
            return mv;
        } else {
            return this.retornaErroCliente("Não foi possível encontrar o cliente #" + id +"!");
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormCliente requisicao) {

        Optional<Cliente> optional = this.clienteRepository.findById(id);

        if (optional.isPresent()) {
            Cliente cliente = optional.get();
            requisicao.fromCliente(cliente);
            ModelAndView mv = new ModelAndView("clientes/edit");
            mv.addObject("page", "clientes/edit");
            mv.addObject("clienteId", cliente.getId());
            return mv;
        } else {
            return this.retornaErroCliente("Não foi possível encontrar o cliente #" + id +"!");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormCliente requisicao, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("clientes/edit");
            mv.addObject("clienteId", id);
            mv.addObject("page", "clientes/edit");
            return mv;
        } else {
            Optional<Cliente> optional = this.clienteRepository.findById(id);
            if (optional.isPresent()) {
                Cliente cliente = requisicao.toCliente(optional.get());
                this.clienteRepository.save(cliente);
                ModelAndView mv = new ModelAndView("redirect:/clientes/" + cliente.getId());
                mv.addObject("page", "clientes/show");
                return mv;
            } else{
                return this.retornaErroCliente("Erro de atualização: Não foi possível encontrar o cliente #" + id +"!");
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/clientes");
        try {
            this.clienteRepository.deleteById(id);
            mv.addObject("mensagem", "Cliente #" + id + " deletado com sucesso!");
            mv.addObject("erro", false);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            mv = this.retornaErroCliente("Erro de delete: Não foi possível encontrar o cliente #" + id +"!");
        }
        return mv;
    }

    private ModelAndView retornaErroCliente(String mensagem) {
        ModelAndView mv = new ModelAndView("redirect:/clientes");
        mv.addObject("mensagem", mensagem);
        mv.addObject("erro", true);
        return mv;
    }
}
