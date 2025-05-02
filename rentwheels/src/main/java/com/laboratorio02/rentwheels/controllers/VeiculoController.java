package com.laboratorio02.rentwheels.controllers;


import com.laboratorio02.rentwheels.dto.RequisicaoFormVeiculo;
import com.laboratorio02.rentwheels.models.Veiculo;
import com.laboratorio02.rentwheels.repositories.VeiculoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping("/veiculos")
    public ModelAndView index(){
        List<Veiculo> veiculos = this.veiculoRepository.findAll();
        ModelAndView mv = new ModelAndView("veiculos/index");
        mv.addObject("veiculos", veiculos);

        return mv;
    }

    public ModelAndView novo(){
        ModelAndView mv = new ModelAndView("veiculos/novo");
        mv.addObject("requisicaoFormVeiculo", new RequisicaoFormVeiculo());
        return mv;
    }

    public ModelAndView create(@Valid RequisicaoFormVeiculo requisicao, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("veiculos/novo");
            return mv;
        } else {
            Veiculo veiculo = requisicao.toVeiculo();
            this.veiculoRepository.save(veiculo);
            return new ModelAndView("redirect:/veiculos");
        }
    }


}
