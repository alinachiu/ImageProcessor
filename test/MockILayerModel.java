import java.io.IOException;
import java.util.List;
import model.color.IColorTransformation;
import model.creator.IImageCreator;
import model.filter.IFilter;
import model.image.IImage;
import model.layer.ILayer;
import model.layer.ILayerModel;
import model.layer.LayerModel;

/**
 *  Represents a mock model for the ILayerModel that allows us to confirm that the controller
 *  is passing the inputs through correctly.
 */
public class MockILayerModel implements ILayerModel {
  private final Appendable log;
  private final ILayerModel delegate;

  /**
   * Constructs the mock model for the ILayerModel using the given appendable that will
   * append a method's parameters to determine if the controller has passed the inputs correctly.
   *
   * @param log the appendable that will append all the input from the controller
   */
  public MockILayerModel(Appendable log) {
    if (log == null) {
      throw new IllegalArgumentException("Cannot have a null appendable");
    }
    this.log = log;
    this.delegate = new LayerModel();
  }

  @Override
  public void createImageLayer(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(name).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public void removeImageLayer(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(name).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public void loadLayer(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(image.getFilename()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public void loadAll(List<ILayer> importedLayers) {
    if (importedLayers == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(importedLayers.toString()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public void makeLayerInvisible(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(layerName).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public void makeLayerVisible(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(layerName).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public List<ILayer> getLayers() {
    return this.delegate.getLayers();
  }

  @Override
  public void setCurrent(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(layerName).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public void filterCurrent(IFilter filter) throws IllegalArgumentException {
    if (filter == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(filter.getClass().getSimpleName()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public void colorTransformCurrent(IColorTransformation colorTransformation) {
    if (colorTransformation == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(colorTransformation.getClass().getSimpleName()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public IImage filter(IImage image, IFilter filter) throws IllegalArgumentException {
    if (image == null || filter == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(image.toString()).append(" ").append(filter.toString()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    return null;
  }

  @Override
  public IImage colorTransformation(IImage image, IColorTransformation colorTransformation)
      throws IllegalArgumentException {
    if (image == null || colorTransformation == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(image.toString()).append(" ").append(colorTransformation.toString()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    return null;
  }

  @Override
  public IImage createImage(IImageCreator creator) throws IllegalArgumentException {
    if (creator == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }
    try {
      log.append(String.valueOf(creator)).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    return null;
  }
}
