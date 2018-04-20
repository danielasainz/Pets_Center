package com.sainz.demo;

/*
You are going to create an application for a community lost and found center for pets.


It will be a Spring Boot application, with the following options:

- List pets

- Add a pets

- Change pet status (lost/found). Do not name your field status.

You must design the application using either your own styles, bootstrap styles, or a combination of the two, and lay out your pages out simply and professionally.

Listing items:

Show a list of all items, and indicate whether they are lost or found.

If an image of the pet is not included, show a default image that has been saved to your application

Adding items:

To add an item, you must enter the following information about it:

Type of animal: (cannot be empty)

Name:

Description:

Currently: (lost or found)

Image

Finding pets

Show a list of pets and allow a user to click 'Report found' or 'Report lost' for each one.  This should change the pet's status.

Making Scheme

- Can I run the Spring application without errors?  5

- Can I see a page that lists items that come from the database? 10

- Can I see whether they are currently lost or have been found? 10

- Can I see images for each item uploaded? 10

 - Can I see a default image if no image is uploaded? 10

- Can I change the status of an item from lost to found and back again? 10

- Did the site make use of the bootstrap grid? 10

- Can I add items to the list? 20

- Can I modify items (e.g. description, status, image)? 10

- Does the application prevent me from entering empty descriptions? 5

 */

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