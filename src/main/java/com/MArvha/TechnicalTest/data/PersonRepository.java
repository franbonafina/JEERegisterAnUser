package com.MArvha.TechnicalTest.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import com.MArvha.TechnicalTest.model.Person;

@ApplicationScoped
public class PersonRepository {

    @Inject
    private EntityManager em;

    public Person findById(Long id) {
        return em.find(Person.class, id);
    }

    public Person findByDni(String dni) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> person = criteria.from(Person.class);     
        criteria.select(person).where(cb.equal(person.get("dni"), dni));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Person> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> member = criteria.from(Person.class);     
       
        criteria.select(member).orderBy(cb.asc(member.get("name")));
        return em.createQuery(criteria).getResultList();
    }
}
