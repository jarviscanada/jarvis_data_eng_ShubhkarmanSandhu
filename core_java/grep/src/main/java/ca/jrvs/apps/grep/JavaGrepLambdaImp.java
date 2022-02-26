package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp{

  public static void main(String[] args) {
    if(args.length == 3){
      JavaGrepLambdaImp obj = new JavaGrepLambdaImp();
      obj.setRegEx(args[0]);
      obj.setOutFile(args[2]);
      obj.setRootPath(args[1]);

      try{
        obj.process();
      } catch (Exception ex){
        //obj.logger.error("Error: Unable to process",ex);
      }


    }
    else{
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }
  }

  @Override
  public List<String> readLines(File inputFile){
    List<String> lineToAdd = new ArrayList<String>();
    try {
      Stream<String> fileStream = Files.lines(inputFile.toPath());
      fileStream.forEach(line -> lineToAdd.add(line));
    } catch(IOException ex) {
     // logger.error("Error file reading",ex);
    }
    return lineToAdd;
  }

  @Override
  public List<File> listFiles(String rootDir){
    List<File> files=null;
    try {
      files = Files.list(Paths.get(rootDir))
          .map(Path::toFile)
          .collect(Collectors.toList());
    } catch (IOException e) {
      // Error while reading the directory
    }
    return  files;
  }
}
