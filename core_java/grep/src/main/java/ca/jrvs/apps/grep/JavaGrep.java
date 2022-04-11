package ca.jrvs.apps.grep;
import java.io.File;
import java.io.IOException;
import java.util.List;
public interface JavaGrep {
  /**
   * Top level search workflow
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverse given directory, return list of files
   * @param rootDir target directory
   * @return files in rootDir
   */
  List<File> listFiles(String rootDir);

  /**
   * Read file, return all lines
   * Use filereader, bufferedreader, character encoding
   * @param inputFile target file to read
   * @return all lines in the file
   * @throws IllegalArgumentException if given file is not a file
   */
  List<String> readLines(File inputFile);

  /**
   * Check if line contains regex set in class,
   * @param line to be checked
   * @return true if pattern exists
   */
  boolean containsPattern(String line);

  /**
   * Write lines to file
   * @param lines to be written
   * @throws IOException if write fails
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegEx();

  void setRegEx(String regEx);

  String getOutFile();

  void setOutFile(String outFile);
}
