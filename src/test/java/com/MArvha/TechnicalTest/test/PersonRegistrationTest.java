package com.MArvha.TechnicalTest.test;

import static org.junit.Assert.assertNotNull;
import java.util.logging.Logger;
import javax.inject.Inject;

import com.MArvha.TechnicalTest.model.Person;
import com.MArvha.TechnicalTest.service.PersonRegistration;
import com.MArvha.TechnicalTest.util.Resources;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PersonRegistrationTest {  
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClasses(Person.class, PersonRegistration.class, Resources.class)
            .addAsResource("META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")            
            .addAsWebInfResource("MAvhaTechnicalTest-DS.xml");
    }

    @Inject
    PersonRegistration memberRegistration;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
        Person newMember = new Person();
        newMember.setName("Franco Bonafina");
        newMember.setAge("26");
        newMember.setDni("36538265");
        memberRegistration.register(newMember);
        assertNotNull(newMember.getId());
        log.info(newMember.getName() + " was persisted with id " + newMember.getId());     
    }

}
