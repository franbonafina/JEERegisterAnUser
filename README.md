# JEE ARQUITECHTURE PROJECT  
### Features
- Maven 3.5
- WildFly Container
- Auth Security
- Injection Dependencies 
- JNDI .
- DataSource -> hsqldb + mysql .
- JPA . Hibernate
- JSF
- Rest Service -> JRXRest
- Pom .

<br></br>
# CONFIGURATIONS : <br></br>
### -1) Clone the repo 
### -2) Configure WildFly to your Local environment
 ### Main Option:
  | -Set or Create environment variable pointing to wildfly folder```export JBOSS_HOME = PATH/WildFLy```
       Note: it depend on the s.o and your preferences to set environment variable .yyy
    Then create an instance, via IDE or Console . So to have a wildfly instance running.   
 Option 2:
    -If u have your wildfly container running. 
      Copy    ```standoloneMav.. .xml```  in standolone folder .
      Copy -recursive ```module/org/hsqldb/main ```  in module folder .
         Note:  if you want to point to another database, it's enought to add dataSource in ```standoloneMa...xml``` reference                 and put in MavhaTecnicaltest-Ds its respective jndi .
          
### Run :```mvn wildfly:deploy```

## Possible Issue
If test failed, inside the path 
```test/MetaInf/arquilian.xml container<"widlfly">```
point to the path of the wildfly .
