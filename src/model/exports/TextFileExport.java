package model.exports;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Represents a class which manages a given {@code IImage} and exports the Image based on this
 * object's {@link Writer} to txt format.
 */
public class TextFileExport implements IExport {

  private final String content;
  private final Writer out;

  /**
   * Constructs a text file exported that has a desired directory name from the user and what will
   * be exported to the text file.
   *
   * @param desiredDir the name of the directory to add files to
   * @param content    the text to be added to the text file.
   * @throws IllegalArgumentException if  any of the given arguments are null
   */
  public TextFileExport(String desiredDir, String content) throws IllegalArgumentException {
    if (desiredDir == null || content == null) {
      throw new IllegalArgumentException("Argument is null");
    }
    this.content = content;
    try {
      this.out = new FileWriter(desiredDir + "layerInfo.txt");
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot write to file");
    }
  }

  /**
   * Constructs a text file exported that has a desired directory name from the user and what will
   * be exported to the text file.
   *
   * @param desiredDir the name of the directory to add files to
   * @param content    the text to be added to the text file.
   * @param out        the specified writer
   * @throws IllegalArgumentException if  any of the given arguments are null
   */
  public TextFileExport(String desiredDir, String content, Writer out)
      throws IllegalArgumentException {
    if (desiredDir == null || content == null || out == null) {
      throw new IllegalArgumentException("Argument is null");
    }
    this.content = content;
    this.out = out;
  }

  @Override
  public void export() throws IOException {
    Writer myWriter = new FileWriter("filename.txt");
    out.write(content);
    myWriter.close();
  }
}
