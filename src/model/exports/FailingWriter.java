package model.exports;

import java.io.IOException;
import java.io.Writer;

/**
 * A mock that represents a {@link Writer} object that fails to write.
 */
public class FailingWriter extends Writer {

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    throw new IOException("fail!");
  }

  @Override
  public void flush() throws IOException {
    throw new IOException("fail!");
  }

  @Override
  public void close() throws IOException {
    throw new IOException("fail!");
  }
}
