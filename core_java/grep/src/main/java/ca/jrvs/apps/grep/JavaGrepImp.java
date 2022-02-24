package ca.jrvs.apps.grep;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  private String regEx;
  private String rootPath;
  private String outFile;

  @Override
  public void process() throws IOException {
   List <String> matchedLines=new ArrayList<String>();

   for(File readFile :this.listFiles(this.getRootPath())){
     for(String readLine: this.readLines(readFile)){
       if(this.containsPattern(readLine)) {
         matchedLines.add(readLine);
       }
     }
    }
   this.writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> files=new ArrayList<File>();
    File root=new File(rootDir);
    for(File readFile:root.listFiles()) {
      files.add(readFile);
    }
    return files;
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines=new ArrayList<String>();
    try {
      FileInputStream fis = new FileInputStream(inputFile);
      Scanner sc = new Scanner(fis);
      while(sc.hasNextLine())
      {
        lines.add(sc.nextLine());
      }
      sc.close();
    }
    catch(IOException ex){

      logger.error("Error file reading",ex);
    }
    return lines;


  }

  @Override
  public boolean containsPattern(String line) {

    return line.matches(this.getRegEx());
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    File out = new File(outFile);
    out.createNewFile();
    BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
    for(String lineToWrite : lines){
      writer.write(lineToWrite);
      writer.newLine();
    }
    writer.close();
  }

  @Override
  public String getRegEx() {
    return regEx;
  }

  @Override
  public void setRegEx(String regEx) {
    this.regEx = regEx;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  public static void main(String[] args) {

    if(args.length!=3){
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }
    //BasicConfigurator.configure();
    JavaGrepImp jGrepImp = new JavaGrepImp();
    jGrepImp.setRegEx(args[0]);
    jGrepImp.setRootPath(args[1]);
    jGrepImp.setOutFile(args[2]);

    try{    //do the actual work process
      jGrepImp.process();
    }catch(Exception ex){

       jGrepImp.logger.error("Error: Unable to process",ex);
    }
  }
}

