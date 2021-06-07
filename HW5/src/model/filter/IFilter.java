package model.filter;

import model.IImage;

/**
 * Represents a filter for an image as well as the operations it can perform (such as applying the
 * filter on an image).
 */
public interface IFilter {
  void apply(IImage image);
}
