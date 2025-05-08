package com.laboratorio02.rentwheels.controllers;


import com.laboratorio02.rentwheels.dto.RequisicaoFormCliente;
import com.laboratorio02.rentwheels.dto.RequisicaoFormVeiculo;
import com.laboratorio02.rentwheels.models.Cliente;
import com.laboratorio02.rentwheels.models.Veiculo;
import com.laboratorio02.rentwheels.repositories.VeiculoRepository;
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
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping("")
    public ModelAndView index(){
        List<Veiculo> veiculos = this.veiculoRepository.findAll();
        ModelAndView mv = new ModelAndView("veiculos/index");
        mv.addObject("veiculos", veiculos);
        mv.addObject("page", "veiculos");
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo(){
        ModelAndView mv = new ModelAndView("veiculos/novo");
        mv.addObject("requisicaoFormVeiculo", new RequisicaoFormVeiculo());
        mv.addObject("page", "veiculos/novo");
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoFormVeiculo requisicao, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("veiculos/novo");
            mv.addObject("page", "veiculos/novo");
            return mv;
        } else {
            Veiculo veiculo = requisicao.toVeiculo();
            this.veiculoRepository.save(veiculo);
            return new ModelAndView("redirect:/veiculos");
        }
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {

        Optional<Veiculo> optional = this.veiculoRepository.findById(id);

        if (optional.isPresent()) {
            Veiculo veiculo = optional.get();
            ModelAndView mv = new ModelAndView("veiculos/show");
            mv.addObject("page", "veiculos/show");
            mv.addObject("veiculo", veiculo);
            return mv;
        } else {
            return this.retornaErroVeiculo("Não foi possível encontrar o veiculo #" + id +"!");
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormVeiculo requisicao) {

        Optional<Veiculo> optional = this.veiculoRepository.findById(id);

        if (optional.isPresent()){
            Veiculo veiculo = optional.get();
            requisicao.fromVeiculo(veiculo);
            ModelAndView mv = new ModelAndView("veiculos/edit");
            mv.addObject("page","veiculos/edit");
            mv.addObject("veiculoId", veiculo.getId());
            return mv;
        } else {
            return this.retornaErroVeiculo("Não foi possivel encontrar o veiculo #" + id +"!");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormVeiculo requisicao, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("veiculos/edit");
            mv.addObject("veiculoId", id);
            mv.addObject("page", "veiculos/edit");
            return mv;
        } else {
            Optional<Veiculo> optional = this.veiculoRepository.findById(id);
            if (optional.isPresent()) {
                Veiculo veiculo = requisicao.toVeiculo(optional.get());
                this.veiculoRepository.save(veiculo);
                ModelAndView mv = new ModelAndView("redirect:/veiculos/" + veiculo.getId());
                mv.addObject("page", "veiculos/show");
                return mv;
            } else{
                return this.retornaErroVeiculo("Erro de atualização: Não foi possível encontrar o veiculo #" + id +"!");
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/veiculos");
        try {
            this.veiculoRepository.deleteById(id);
            mv.addObject("mensagem", "Veiculo #" + id + " deletado com sucesso!");
            mv.addObject("erro", false);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            mv = this.retornaErroVeiculo("Erro de delete: Não foi possível encontrar o veiculo #" + id +"!");
        }
        return mv;
    }

    private ModelAndView retornaErroVeiculo(String mensagem) {
        ModelAndView mv = new ModelAndView("redirect:/veiculos");
        mv.addObject("mensagem", mensagem);
        mv.addObject("erro", true);
        return mv;
    }

}
