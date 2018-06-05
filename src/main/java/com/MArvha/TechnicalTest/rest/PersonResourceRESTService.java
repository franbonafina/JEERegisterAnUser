package com.MArvha.TechnicalTest.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.MArvha.TechnicalTest.data.PersonRepository;
import com.MArvha.TechnicalTest.model.Person;
import com.MArvha.TechnicalTest.service.PersonRegistration;

@Path("/personas")
@RequestScoped
public class PersonResourceRESTService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private PersonRepository repository;

    @Inject
    PersonRegistration registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> listAllMembers() {
        return repository.findAllOrderedByName();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person lookupMemberById(@PathParam("id") long id) {
        Person member = repository.findById(id);
        if (member == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return member;
    }

    /** Creates a new member from the values provided.  */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson(Person person) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates person using bean validation
            validatePerson(person);

            registration.register(person);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("dni", "DNI taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    /**Validates the given Person variable and throws validation exceptions based on the type of error.*/
    private void validatePerson(Person person) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Person>> violations = validator.validate(person);      
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<>(violations));
        }             
        if (dniAlreadyExists(person.getDni())) {
            throw new ValidationException("Unique Dni Violation");
        } 
    }

    /**Creates a JAX-RS "Bad Request" response .*/
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    /**
     * Checks if a member with the same DNI is already registered. 
     */
    public boolean dniAlreadyExists(String dni) {
        Person person = null;
        try {
            person = repository.findByDni(dni);
        } catch (NoResultException e) {
            // ignore
        }
        return person != null;
    }
}
