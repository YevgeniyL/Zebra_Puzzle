# Zebra_Puzzle
One of solution for Zebra Puzzle (https://en.wikipedia.org/wiki/Zebra_Puzzle)

Based on Java 8.

Build tool is 'maven 3'.

Used 'Junit 4' framework for testing.


Folder 'zebra' contain additional files:
- 'zebra-jar-with-dependencies.jar' final application
- 'input.txt" - sourse file with valid predicates. Application use it in work
- 'output.xml" - generated file with solution after application works
- 'stylesheet.xsl' - template for create HTML code
-'generated-page.html' - example of HTML page. Can be generated from code by test 'HtmlSolution'

In original puzzle said rule is 'neighbors'. 
But in this task clear terms are used 'left' and 'right'.

# Task
A program, which resolves all possible solutions of this puzzle with original rules. It would be a plus if your program will be able to solve similar puzzle for other set of rules.
The input data format provided just as an example. You can use our format, but if you dont like it, feel free to change it.
Please leave the output XML format the same as provided in example.
In addition please provide an XSL to transform result XML to view presentation in HTML like this http://en.wikipedia.org/wiki/Zebra_Puzzle#Solution
Be sure to keep the code design simple, but allowing to easily change or add more input sources and/or output file formats.
Unit tests for the code are mandatory. No external libraries are allowed in addition to the Java standard API except JUnit.
Try to keep your code as readable as possible. We value code simplicity. Use object-oriented approach with common design patterns where applicable.

Example of the input file:
```
SAME;nationality;English;color;Red

SAME;nationality;Spaniard;pet;Dog

SAME;drink;Coffee;color;Green

SAME;drink;Tea;nationality;Ukrainian

TO_THE_LEFT_OF;color;Ivory;color;Green

SAME;smoke;Old gold;pet;Snails

SAME;smoke;Kools;color;Yellow

SAME;drink;Milk;position;3

SAME;nationality;Norwegian;position;1

NEXT_TO;smoke;Chesterfields;pet;Fox

TO_THE_LEFT_OF;smoke;Kools;pet;Horse

SAME;smoke;Lucky strike;drink;Orange juice

SAME;smoke;Parliaments;nationality;Japanese

NEXT_TO;color;Blue;nationality;Norwegian

SAME;drink;Water

SAME;pet;Zebra
```

Example of the output file output.xml:
```
<solutions>
 <solution>
    <house position="1" color="Yellow" nationality="Norwegian" drink="Water"        smoke="Kools"         pet="Fox"/>
    <house position="2" color="Blue"   nationality="Ukrainian" drink="Tea"          smoke="Chesterfields" pet="Horse"/>
    <house position="3" color="Red"    nationality="English"   drink="Milk"         smoke="Old gold"      pet="Snails"/>
    <house position="4" color="Ivory"  nationality="Spaniard"  drink="Orange juice" smoke="Lucky strike"  pet="Dog"/>
    <house position="5" color="Green"  nationality="Japanese"  drink="Coffee"       smoke="Parliaments"   pet="Zebra"/>
  </solution>
</solutions>
