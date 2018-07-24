# Register New Person
# Register new person JEE + Rest .
<br></br>
CONFIGURATIONS : <br></br>
#Maven 3.5<br></br>#JAva 1.8 (or 1.7)<br></br>
### Set JBOSS_HOME(point to wildfly folder) Or set standoloneMav.. .xml  in Your WildFly/JBOSS and add module/org/hsqldb/main  
(or add dataSource in standoloneMav..xml reference to another dataBase . Then put in MAvhaTecnicaltest-Ds its respective jndi )<br></br>
#In the folder run mvn wildfly:deploy <br></br>
## If test failed, test/MetaInf/arquilian.xml container<"widlfly">.. point to the path of the wildfly .
