package ru.youlola.spring.demoSite.dto;

import java.util.List;

public class PersonResponse {
    private List<PersonDTO> person;

    public PersonResponse(List<PersonDTO> person) {
        this.person = person;
    }

    public List<PersonDTO> getPerson(){
        return person;
    }
    public void setPerson(List<PersonDTO> person){
        this.person=person;
    }
}
