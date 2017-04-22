@ECHO off
ECHO / / / / / Building Resources / / / / /
ECHO Compiling...
javac BuildResources.java
ECHO Running...
java BuildResources
ECHO Cleaning...
DEL BuildResources.class
CD ../
ECHO Done!
ECHO.