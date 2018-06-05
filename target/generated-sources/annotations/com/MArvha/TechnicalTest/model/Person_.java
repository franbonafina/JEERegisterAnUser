package com.MArvha.TechnicalTest.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ {

	public static volatile SingularAttribute<Person, String> name;
	public static volatile SingularAttribute<Person, Long> id;
	public static volatile SingularAttribute<Person, String> age;
	public static volatile SingularAttribute<Person, String> dni;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String AGE = "age";
	public static final String DNI = "dni";

}

