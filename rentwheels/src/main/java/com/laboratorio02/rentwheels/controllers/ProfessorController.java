package com.laboratorio02.rentwheels.controllers;

import com.laboratorio02.rentwheels.dto.RequisicaoFormProfessor;
import com.laboratorio02.rentwheels.models.Professor;
import com.laboratorio02.rentwheels.models.StatusProfessor;
import com.laboratorio02.rentwheels.repositories.ProfessorRepository;
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
            mv.addObject("page", "professores/novo");
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
            return this.retornaErroProfessor("SHOW ERROR: Professor #" + id + " não encontrado no banco!");
        }
    }


    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormProfessor requisicao) {

        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent()) {
            Professor p = optional.get();
            requisicao.fromProfessor(p);
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("page", "professores/edit");
            mv.addObject("professorId", p.getId());
            mv.addObject("statusProfessor", StatusProfessor.values());
            return mv;
        } else {
            return this.retornaErroProfessor("EDIT ERROR: Professor #" + id + " não encontrado no banco!");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", id);
            mv.addObject("page", "professores/edit");
            mv.addObject("statusProfessor", StatusProfessor.values());
            return mv;
        } else {
            Optional<Professor> optional = this.professorRepository.findById(id);
            if (optional.isPresent()) {
                Professor professor = requisicao.toProfessor(optional.get());
                this.professorRepository.save(professor);
                ModelAndView mv = new ModelAndView("redirect:/professores/" + professor.getId());
                mv.addObject("page", "professores/show");
                return mv;
            } else{
                return this.retornaErroProfessor("UPDATE ERROR: Professor #" + id + " não encontrado no banco!");
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("redirect:/professores");
        try {
            this.professorRepository.deleteById(id);
            mv.addObject("mensagem", "Professor #" + id + " deletado com sucesso!");
            mv.addObject("erro", false);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            mv = this.retornaErroProfessor("DELETE ERROR: Professor #" + id + " não encontrado no banco!");
        }
        return mv;
    }

    private ModelAndView retornaErroProfessor(String mensagem) {
        ModelAndView mv = new ModelAndView("redirect:/professores");
        mv.addObject("mensagem", mensagem);
        mv.addObject("erro", true);
        return mv;
    }
}