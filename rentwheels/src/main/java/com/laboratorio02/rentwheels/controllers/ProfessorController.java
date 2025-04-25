package com.laboratorio02.rentwheels.controllers;

import com.laboratorio02.rentwheels.models.Professor;
import com.laboratorio02.rentwheels.models.StatusProfessor;
import com.laboratorio02.rentwheels.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("/professores")
    public ModelAndView index(){
//        Professor batman = new Professor("Batman", new BigDecimal(5000.0), StatusProfessor.ATIVO);
//        batman.setId(1L);
//        Professor coringa = new Professor("Coringa", new BigDecimal(10000.0), StatusProfessor.APOSENTADO);
//        coringa.setId(2L);
//        Professor mulherMaravilha = new Professor("Mulher Maravilha", new BigDecimal(15000.0), StatusProfessor.INATIVO);
//        mulherMaravilha.setId(3L);
//        List<Professor> professores = Arrays.asList(batman, coringa, mulherMaravilha);

        List<Professor> professores = this.professorRepository.findAll();

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);

        return mv;
    }
    @GetMapping("/professores/novo")
    public ModelAndView novo() {

        ModelAndView mv = new ModelAndView("professores/novo");
        mv.addObject("statusProfessor", StatusProfessor.values());
        return mv;
    }
}
