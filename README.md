CLAP
====

CLAP (Command-Line Argument Parser) is a Java library to parse easily the command-line argument of a java program.

How to use
-----------

To use it, you have to create an OptionSet and fill it with options.
Then, the parser will modify the options (if defined, if there's a value...).

There is two types of options : 
  - "Option", for example : "-v" or "--version"
  - "OptionWithValue", for example : "-s 10" or "--size=10"
  
The short options can be in one argument. For example : "-abs 10" = "-a -b -s 10"

See "samples" folder for some example.


How to build
-----------

Build CLAP jar:
``` bash
mvn install # or "mvn clean install" to clean and build
```


Build and run CLAP examples ("samples" folder):
``` bash
ant jar
java -jar dist/CLAP_project_name.jar
```


Tools
-----

- CLAP is a Maven project (Maven 3), and Java 7 is required (see pom.xml).
- CLAP samples are ANT projects (ANT 1.9)
- JUnit 4.11 is used for the unit tests.
- JaCoCo (Netbeans plugin) is used to check the unit tests coverage.
