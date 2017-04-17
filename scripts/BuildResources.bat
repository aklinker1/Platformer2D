@ECHO off
ECHO / / / / / Building Resources / / / / /
ECHO Compiling...
CD C:\Users\aaron\Programming\Java\Platformer2D\scripts
java BuildResources.java
ECHO Running...
java BuildingResources.class
ECHO Cleaning...
DEL BuildingResources.class
CD ../
ECHO Done!
ECHO.

ECHO  / / / / / Building Gradle / / / / /
gradlew build run