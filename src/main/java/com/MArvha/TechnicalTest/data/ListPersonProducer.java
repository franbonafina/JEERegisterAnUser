package com.MArvha.TechnicalTest.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import com.MArvha.TechnicalTest.model.Person;

@RequestScoped
public class ListPersonProducer {

    @Inject
    private PersonRepository personRepository;
    
    
    private List<Person> members;
    
    @Produces  
    @Named
    public List<Person> getMembers() {
        return members;
    }

    public void onPersonListChanged(@Observes final Person person) {
        giveAllPersonOrderedByName();
    }

    @PostConstruct
    public void giveAllPersonOrderedByName() {
        members = personRepository.findAllOrderedByName();
    }
}
