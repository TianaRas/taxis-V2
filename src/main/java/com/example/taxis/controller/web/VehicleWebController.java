package com.example.taxis.controller.web;

import com.example.taxis.service.ApplicationService;
import com.example.taxis.dto.request.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
@Controller
@RequestMapping("/vehicles")
public class VehicleWebController {
    private final ApplicationService service;
    public VehicleWebController(ApplicationService service) { this.service=service; }
    @GetMapping public String list(@RequestParam(defaultValue="") String q, @RequestParam(defaultValue="") String status, @RequestParam(defaultValue="") String filter, Pageable pageable,Model m) {
        prepare(m); m.addAttribute("page",service.listVehicle(q,status,filter,pageable)); m.addAttribute("q",q); m.addAttribute("status",status); m.addAttribute("filter",filter); m.addAttribute("title","Véhicules"); m.addAttribute("path","/vehicles"); return "vehicle-list";
    }
    private void prepare(Model m) {
        m.addAttribute("title","Véhicules"); m.addAttribute("path","/vehicles");

    }
    @GetMapping("/new") @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATOR')") public String form(Model m) { prepare(m); m.addAttribute("editing",false); return "vehicle-form"; }
    @GetMapping("/{id}/edit") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public String edit(@PathVariable Long id,Model m) { prepare(m); m.addAttribute("editing",true); m.addAttribute("id",id); m.addAttribute("record",service.getVehicle(id)); return "vehicle-form"; }
    @GetMapping("/{id}") public String detail(@PathVariable Long id,Model m) { prepare(m); m.addAttribute("record",service.getVehicle(id)); m.addAttribute("audit",service.history("Vehicle",id)); service.detail("Vehicle",id,m); return "vehicle-detail"; }
    @PostMapping @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATOR')") public String save(@Valid @ModelAttribute("record") VehicleRequest r,BindingResult errors,Model m,RedirectAttributes flash) {
        if(errors.hasErrors()) { prepare(m); m.addAttribute("editing",false); m.addAttribute("error",errors.getAllErrors().get(0).getDefaultMessage()); return "vehicle-form"; }
        try { var saved=service.createVehicle(r); flash.addFlashAttribute("success","Enregistrement créé"); return "redirect:/vehicles/"+saved.id(); }
        catch(com.example.taxis.exception.BusinessException e) { prepare(m); m.addAttribute("editing",false); m.addAttribute("error",e.getMessage()); return "vehicle-form"; }
    }
    @PostMapping("/{id}/edit") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public String update(@PathVariable Long id,@Valid @ModelAttribute("record") VehicleRequest r,BindingResult errors,Model m,RedirectAttributes flash) {
        if(errors.hasErrors()) { prepare(m); m.addAttribute("editing",true); m.addAttribute("id",id); m.addAttribute("error",errors.getAllErrors().get(0).getDefaultMessage()); return "vehicle-form"; }
        try {service.updateVehicle(id,r); flash.addFlashAttribute("success","Modifications enregistrées"); return "redirect:/vehicles/"+id;}
        catch(com.example.taxis.exception.BusinessException e) {prepare(m); m.addAttribute("editing",true);m.addAttribute("id",id);m.addAttribute("error",e.getMessage());return "vehicle-form";}
    }
    @PostMapping("/{id}/delete") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public String delete(@PathVariable Long id,RedirectAttributes flash) {service.deleteVehicle(id);flash.addFlashAttribute("success","Enregistrement supprimé");return "redirect:/vehicles";}
}
