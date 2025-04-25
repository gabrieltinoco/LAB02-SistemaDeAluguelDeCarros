package com.laboratorio02.rentwheels.controllers;

import com.laboratorio02.rentwheels.dto.RequisicaoFormCliente;
import com.laboratorio02.rentwheels.models.Cliente;
import com.laboratorio02.rentwheels.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

        return mv;
    }

    @GetMapping("/clientes/novo")
    public ModelAndView novo() {
        return new ModelAndView("clientes/novo");
    }

    @PostMapping("/clientes")
    public String create(RequisicaoFormCliente requisicao) {
        Cliente cliente = requisicao.toCliente();
        this.clienteRepository.save(cliente);
        return "redirect:/clientes";
    }
}
