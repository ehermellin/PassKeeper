# PassKeeper

***Note**: Be aware that this application is not a "professional" one. 
Even if the backup file is encrypted, the algorithms used and their implementation 
are **not secure proof** and are certainly **outdated**. I can not therefore be held responsible 
for the dangerous uses in terms of security that you make of this software*

![PassKeeper](https://raw.githubusercontent.com/ehermellin/PassKeeper/master/PassKeeper.png)

### Dependency

Java JDK 8 must be installed on the computer that will run PassKeeper:

**On windows**

Go to Oracle website and download the JDk corresponding to your computer architecture (x64 or x86): [Java](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

After the installation, you have to add java in the *PATH*.

**On Linux (Debian / Ubuntu)**

    sudo apt-get install openjdk-8-jdk
    
**On MacOS**
  
### How to use the PDDL4GUI
Download or clone the repository.

With Gradle :

    # Build PassKeeper (compile, assemble, jar, checkstyle, findbugs)
    ./gradlew build
    # Generate .jar file
    ./gradlew jar
    # Generate javadoc
    ./gradlew javadoc
    # Run PassKeeper
    ./gradlew run
    # Run PassKeeper with LOG panel
    ./gradlew run -PArgs=-LOG

With java command lines:

    java -jar Passkeeper-X.X.jar
    java -jar Passkeeper-X.X.jar -LOG