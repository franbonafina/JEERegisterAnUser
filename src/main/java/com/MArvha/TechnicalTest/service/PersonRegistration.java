package com.MArvha.TechnicalTest.service;

import com.MArvha.TechnicalTest.model.Person;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;


@Stateless
public class PersonRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;    

    public void register(Person member) throws Exception {
        log.info("Registering " + member.getName());
        em.persist(member);     
    }   
   
}
