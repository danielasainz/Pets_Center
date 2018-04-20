package com.sainz.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Controller

public class HomeController {

    @Autowired
    petRepository petRepo;

    @RequestMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("center", petRepo.findAll());
        return "index";
    }

    @RequestMapping("/add")
    public String addPet(Model model) {
        model.addAttribute("aPet", new Pet());
        return "addPet";
    }

    @RequestMapping("/savepet")
    public String savePet(@Valid @ModelAttribute("aPet") Pet toSave, BindingResult result) {

        if (result.hasErrors()) {
            return "addPet";
        }

        /*

        can't figure out this code below:
        if ("${eachPet.image.equals("")}) {
        return "/image/puppy.jpg";
        }
        */

        petRepo.save(toSave);
        return "redirect:/";
    }

    @RequestMapping("/changestatus/{id}")
    public String lostFound(@PathVariable("id") long id) {
        Pet thisPet = petRepo.findById(id).get();
        thisPet.setAvailable(!thisPet.getAvailable());
        petRepo.save(thisPet);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateCar(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("aPet", petRepo.findById(id).get());
        return "addPet";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") long id){
        petRepo.deleteById(id);
        return "redirect:/";

    }

}