# How To Run

 To run selenium Task CMD to project's location and use:
 1. mvn -Dsurefire.suiteXmlFiles=src/test/resources/TestSuites/LegoSuite.xml -Dbrowser=Chrome test 
 2. Using InteliJ Run configuration.
 
 Edge can be chosen too via  -Dbrowser=Edge or not even passing this parameter because this default choice for my framework.
 ![image](https://user-images.githubusercontent.com/25178870/156804102-dd6e0b5a-a8d6-480f-99da-98c3de5c6f44.png)

# Warning

Target Java version is 11. Change <maven.compiler.target>11</maven.compiler.target> in pom.xml to your choice of Java.
If new java downloaded don't forget to create enviroment variables in Windows.
