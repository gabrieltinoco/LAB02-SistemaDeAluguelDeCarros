package com.laboratorio02.rentwheels.controllers;

import com.laboratorio02.rentwheels.dto.RequisicaoFormProfessor;
import com.laboratorio02.rentwheels.models.Professor;
import com.laboratorio02.rentwheels.models.StatusProfessor;
import com.laboratorio02.rentwheels.repositories.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("")
    public ModelAndView index() {

        List<Professor> professores = this.professorRepository.findAll();
        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);
        mv.addObject("page", "professores");

        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("professores/novo");
        mv.addObject("requisicaoFormProfessor", new RequisicaoFormProfessor()); // <-- linha essencial
        mv.addObject("statusProfessor", StatusProfessor.values());
        mv.addObject("page", "professores/novo");
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("professores/novo");
            mv.addObject("statusProfessor", StatusProfessor.values());
            return mv;
        } else {
            Professor professor = requisicao.toProfessor();
            this.professorRepository.save(professor);
            return new ModelAndView("redirect:/professores/" + professor.getId());
        }
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {

        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent()) {
            Professor p = optional.get();
            ModelAndView mv = new ModelAndView("professores/show");
            mv.addObject("page", "professores/show");
            mv.addObject("professor", p);
            return mv;
        } else {
            return new ModelAndView("redirect:/professores");
        }
    }

}