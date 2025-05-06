package com.laboratorio02.rentwheels.controllers;

import com.laboratorio02.rentwheels.dto.RequisicaoFormCliente;
import com.laboratorio02.rentwheels.dto.RequisicaoFormProfessor;
import com.laboratorio02.rentwheels.models.Cliente;
import com.laboratorio02.rentwheels.models.StatusProfessor;
import com.laboratorio02.rentwheels.repositories.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public ModelAndView index() {
        List<Cliente> clientes = clienteRepository.findAll();

        ModelAndView mv = new ModelAndView("clientes/index");
        mv.addObject("clientes", clientes);
        mv.addObject("page", "clientes");
        return mv;
    }

    @GetMapping("/clientes/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("clientes/novo");
        mv.addObject("requisicaoFormCliente", new RequisicaoFormCliente());
        mv.addObject("page", "clientes/novo");
        return mv;
    }

    @PostMapping("/clientes")
    public ModelAndView create(@Valid RequisicaoFormCliente requisicao, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("clientes/novo");
            return mv;
        } else {
            Cliente cliente = requisicao.toCliente();
            this.clienteRepository.save(cliente);
            return new ModelAndView("redirect:/clientes");
        }
    }
}
