JCLAP
====

JCLAP (Java Command-Line Argument Parser) is a Java library to parse easily the command-line argument of a java program.

How to build
-----------

Build JCLAP jar:
``` bash
mvn install # or "mvn clean install" to clean and build
```


Build and run JCLAP examples ("samples" folder):
``` bash
ant jar
java -jar dist/JCLAP_project_name.jar
```


How to use
-----------

To use it, you have to create an OptionSet and fill it with options.
Then, the parser will modify the options (if defined, if there's a value...).

There is two types of options : 
  - "Option", for example : "-v" or "--version"
  - "OptionWithValue", for example : "-s 10" or "--size=10"
  
The short options can be in one argument. For example : "-abs 10" = "-a -b -s 10"

See "samples" folder for some example.


Options creation
----------------

You can create the options : 
- In the Java code, using the Option and OptionWithValue classes (see "samples/JCLAP_FileReader" project).

Note: The options Java objects can be exported in a XML file.

``` java
// optLines : "-l NBLINES, --lines=NBLINES"
optLines = new OptionWithValue('l', "lines", "output only NBLINES lines");
optLines.setValueName("NBLINES");
// optNumber : "-n, --number"
optNumber = new Option('n', "number", "number all output lines");
// optHelp : "--help"
optHelp = new Option((char) 0, "help", "display this help and exit");
```

- Using a XML file (see "samples/JCLAP_FileReader_XMLOptions" project)

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<options>
    <option-value>
        <shortname>l</shortname>
        <longname>lines</longname>
        <helpmsg>output only NBLINES lines</helpmsg>
        <value-name>NBLINES</value-name>
    </option-value>
    <option>
        <shortname>n</shortname>
        <longname>number</longname>
        <helpmsg>number all output lines</helpmsg>
    </option>
    <option>
        <longname>help</longname>
        <helpmsg>display this help and exit</helpmsg>
    </option>
</options>

```

Tools
-----

- JCLAP is a Maven 3 project.
- Java 7 is required (see pom.xml).
- Samples are ANT projects (ANT 1.9).
- JUnit 4.11 is used for the unit tests.
- JaCoCo (Netbeans plugin) is used to check the unit tests coverage.
