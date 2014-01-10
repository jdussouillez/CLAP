CLAP
====

CLAP (Command-Line Argument Parser)

- Description
- 
CLAP is Java library to parse easily the command-line argument of a java program.
To use it, you have to create an OptionSet and fill it with options.
Then, the parser will modify the options (if defined, if there's a value...).

There is two types of options : 
  "Option". For example : "-v" or "--version"
  "OptionWithValue". For example : "-s 10" or "--size=10"
  
The short options can be in one argument. For example : "-abs 10" = "-a -b -s 10"

See "samples" folder for some example.


- Tools
CLAP is a Maven project (Maven 3.0.4) and Java 7 is required (see pom.xml)
JUnit 4.11 is used for the unit tests.
JaCoCo (Netbeans plugin) is used to check the unit tests coverage
