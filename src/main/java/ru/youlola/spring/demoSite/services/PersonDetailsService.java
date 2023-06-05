package ru.youlola.spring.demoSite.services;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.youlola.spring.demoSite.dto.PersonDTO;
import ru.youlola.spring.demoSite.models.Person;
import ru.youlola.spring.demoSite.repositories.PeopleRepository;
import ru.youlola.spring.demoSite.security.PersonDetails;

import java.util.List;
import java.util.Objects;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;
    private ModelMapper modelMapper;
    private  PasswordEncoder passwordEncoder;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository, ModelMapper modelMapper) {
        this.peopleRepository = peopleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Person person = peopleRepository.findByUsername(s);
        if (person == null)
            throw new UsernameNotFoundException("User not found");
        return new PersonDetails(person);
    }

    @Transactional
    public void updateProfile(PersonDTO dto) {
        Person savedPerson = peopleRepository.findByUsername(dto.getUsername());
        if (savedPerson == null){
            throw new RuntimeException("User not found by name " +dto.getUsername());
        }

        boolean isChanged = false;
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()){
            savedPerson.setPassword(passwordEncoder.encode(dto.getPassword()));
            isChanged = true;
        }

        if(!Objects.equals(dto.getUsername(), savedPerson.getUsername())){
            savedPerson.setUsername(dto.getUsername());
            isChanged = true;
        }

        if(isChanged) {
            peopleRepository.save(savedPerson);
        }
    }


    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

}
