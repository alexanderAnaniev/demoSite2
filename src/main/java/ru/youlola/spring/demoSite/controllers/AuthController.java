package ru.youlola.spring.demoSite.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.youlola.spring.demoSite.dto.PersonDTO;
import ru.youlola.spring.demoSite.models.Person;

import ru.youlola.spring.demoSite.repositories.PeopleRepository;
import ru.youlola.spring.demoSite.services.PersonDetailsService;
import ru.youlola.spring.demoSite.services.RegistrationService;
import ru.youlola.spring.demoSite.util.PersonValidator;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

//@RestController
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final    PersonDetailsService personDetailsService;
    private final PeopleRepository peopleRepository;

    private final PersonValidator personValidator;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonDetailsService personDetailsService, PeopleRepository peopleRepository, PersonValidator personValidator, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.personDetailsService = personDetailsService;
        this.peopleRepository = peopleRepository;
        this.personValidator = personValidator;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid
                                          Person person,
                                      BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {

            return "/auth/registration";

        }else {
               registrationService.register(person);
        }

            return "/successRegister";
        }



        @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "/auth/login";
        }

        @GetMapping("/profile")
        public String profileUser(Model model, Principal principal) {
            if (principal == null) {
                throw new RuntimeException("You are not authorize");
            }
            Person person = peopleRepository.findByUsername(principal.getName());

            PersonDTO dto = PersonDTO.builder()
                            .username(person.getUsername())
                                    .email(person.getEmail())
                                            .build();

            model.addAttribute("person",dto);
            return "profile";

        }

        @PostMapping("/profile")
        public String updateProfileUser(PersonDTO dto,Model model,Principal principal) {
            if (principal == null || !Objects.equals(principal.getName(), dto.getUsername())) {
                throw new RuntimeException("You are not authorize");
            }
            if (dto.getPassword() != null
                    && !dto.getPassword().isEmpty())
            {
                model.addAttribute("person", dto);
                return "profile";
            }
            personDetailsService.updateProfile(dto);
            return "redirect:/auth/profile";
        }
}


