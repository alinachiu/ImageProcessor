package model.colorTransformation;

import model.IImage;

/**
* This interface constructs methods to be used on applying a color transformation on an image.
*/
public interface IColorTransformation {
  void apply(IImage image);
}
