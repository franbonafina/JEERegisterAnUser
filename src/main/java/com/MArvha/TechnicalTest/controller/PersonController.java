package com.MArvha.TechnicalTest.controller;


import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

//import com.MArvha.TechnicalTest.rest.PersonResourceRESTService;
import com.MArvha.TechnicalTest.model.Person;
import com.MArvha.TechnicalTest.service.PersonRegistration;
import com.MArvha.TechnicalTest.data.PersonRepository;
import javax.persistence.NoResultException;
import javax.validation.ValidationException;

@Model
public class PersonController {

    @Inject
    private FacesContext facesContext;
    
    // @Inject
    // private PersonResourceRESTService restService;
    
    @Inject
    private PersonRegistration personRegistration;
    
    @Inject 
    private PersonRepository repository;

    @Produces
    @Named
    private Person newPerson;

    @PostConstruct
    public void initNewMember() {
        newPerson = new Person();
    }

    public void register() throws Exception {
        try {  
            validatePerson(newPerson);
            personRegistration.register(newPerson);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
            //initNewMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {            
            return errorMessage;
        }
        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
       
        return errorMessage;
    }
    
    private void validatePerson(Person person) throws ValidationException {
         if (dniAlreadyExists(person.getDni())) {
            throw new ValidationException("UNIQUE DNI VIOLATION !");
        } 
    }
         
    private boolean dniAlreadyExists(String dni) {
        Person person = null;
        try {
            person = repository.findByDni(dni);
        } catch (NoResultException e) {         
        }
        return person != null;
    }  

}
