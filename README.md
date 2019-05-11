# Devflection class loaders example project

This project demonstrates how we can extend Java class loaders to 
load classes from alternate sources, in this specific example we have a HSQLDB 
from which we load the classes.

## How to run the example project

First cd into the project

    cd ClassLoadersExample
    
Then we have to compile the application

    mvn compile
    
Then we can run it directly from the command line using maven

    mvn exec:java -Dexec.mainClass="com.devflection.Main"
 