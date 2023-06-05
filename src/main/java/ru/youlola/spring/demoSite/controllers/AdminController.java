package ru.youlola.spring.demoSite.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import ru.youlola.spring.demoSite.dto.PersonDTO;
import ru.youlola.spring.demoSite.models.Person;
import ru.youlola.spring.demoSite.security.PersonDetails;
import ru.youlola.spring.demoSite.services.AdminService;
import ru.youlola.spring.demoSite.services.PersonDetailsService;
import ru.youlola.spring.demoSite.services.ProductService;

import java.util.stream.Collectors;

@Controller
public class AdminController {
private final ModelMapper modelMapper;
private final PersonDetailsService personDetailsService;
    private final AdminService adminService;
    private final ProductService productService;
    @Autowired
    public AdminController(ModelMapper modelMapper, PersonDetailsService personDetailsService, AdminService adminService, ProductService productService) {
        this.modelMapper = modelMapper;
        this.personDetailsService = personDetailsService;
        this.adminService = adminService;
        this.productService = productService;
    }


    @GetMapping("/showUserInfo")
    public String showUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return personDetails.getUsername();
    }

    @GetMapping("/admin")
    public String adminPage(){
        adminService.doAdminStuff();
        return "admin";
    }

    @GetMapping("/users")
    public String userList(Model model){
        model.addAttribute("persons", personDetailsService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList()));
        return "userList";
    }




    public PersonDTO convertToPersonDTO(Person person){
        return this.modelMapper.map(person, PersonDTO.class);
    }
    public Person convertToPerson(PersonDTO personDTO){
        return  modelMapper.map(personDTO, Person.class);
    }

}


