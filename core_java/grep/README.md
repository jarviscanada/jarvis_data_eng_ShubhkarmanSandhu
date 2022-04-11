# Introduction 
Java grep is an app that impersonates the functionality of the grep bash command of Linux(Match pattern in files in a given directory). App accepts a directory path, then reads all files with the help of `java.io` library and uses `java.util.regex` to match a given pattern in the file line by line. Matched lines are saved in the output file through BufferedWriter.There are two different implementations of grep with and without Stream API & Lambda. This application has been wrapped in a FatJar and a special dockerfile is implemented for docker image creation. Error handling is done by slf4j loggers library.

# Quick Start
   #### Directory:  ```cd.. core_java/grep```<br>
  #### Build the app with maven: ```mvn clean compile package```<br>
#### Speical Arguments:-
 - [ReadDirectory]=Directory where its files will be searched by app, ex:-  . ./data/txt/  
 - [OutputFile]=Out file where results will be saved, ex:-  . ./out/grep.txt  
 - [SearchPattern]=regex of pattern to match, ex:-  .*Romeo.*Juliet.* <br>
    
 #### Jar file execution: 
 ``` java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp [SearchPattern] [ReadDirectory] [OutputFile]```<br>
 #### Docker container execution:
         
 - ```docker build -t [ImageName]```
 - ```docker run --rm -v  `pwd`/data:/data -v  `pwd`/log:/log [ImageName] [SearchPattern] [ReadDirectory] [OutputFile]```<br>
 *please be careful with the relative path

# Implemenation 

## Pseudocode 
```
matchedLines = []
for file in listFilesRecursively(rootDir)
    for line in readLines(file)
        if containsPattern(line)
            matchedLines.add(line)
writeToFile(matchedLines)
```
## Performance Issue 
Memory and Performance issues can occur as is this app we doing a lot of reading and writing of files, if file directory is bigger or if files are huge then the app can take a significant amount to produce output and even crash at some stage.<br>
Memory issues can be solved by controlling the memory of the heap in the JVM according to input file size. Oracle® HotSpot™ options can be used to set the initial/minimum Java™ heap size by ```-Xms```  cmd, and the maximum heap size by ```-Xmx``` cmd.<br>

Performance issues are solved by using Lambda and Stream API instead of for loops to read directories and files as API has higher efficiency and  utilize Multicore of CPU to increase performance 
 
# Test 
A special text file was used to test the integrity and performance of the app. The file contained some text from  Romeo Juilet Play and regex patterns were used to test if the application gave correct output, and performance was noted with and without lambda and stream API.<br>
File:- shakespeare.txt <br>
Pattern:- .*Romeo.*Juliet.* <br>
Output :-   
 Is father, mother, Tybalt, Romeo, Juliet, <br>
Enter Romeo and Juliet aloft, at the Window.<br>
 And Romeo dead; and Juliet, dead before,<br>
Romeo, there dead, was husband to that Juliet;<br>

# Deployment 
The app is developed in two ways:-

1.  Jar file:- Jar file has been developed using maven(Wrapped with fatjar to contain all the dependencies)<br>

2. Docker image:- Docker image has been created and pushed to dockerHub as lordsandhu/grep image file

*To run these files check [QuickStart](#Quick-Start).
# Improvement 
1. Find multiple regex patterns in files
2. Add options to skip certian type of Files
3. More use of Lamda and Stream API to increase performance 
