## Command Overview
- blur: blurs the current image
- sharpen: sharpens the current image
- sepia: applies sepia onto the current image
- grayscale: applies grayscale onto the current image
- create: creates a new layer image based on a user supplied name for the layer
- remove: removes a layer based on the given name from the user
- current: makes the layer associated with the given name the current layer
- load: loads an existing image to the current layer, will not load in layer if no layers exist yet
- loadall: loads an existing multi-layered image based on a given text file
- save: saves the topmost, visible layer
- saveall: saves the entire multi-layered image in a folder of a given name as well as a text file named layerInfo.txt which contains the information for each layer in the model
- invisible: makes a layer invisible based on a given layer name
- visible: makes a layer visible based on a given layer name
- colortransform: performs a color transformation on a given image and adds it to the list of layers using a filter based on user input
- createdefaultimage: creates a new checkerboard image with default colors, and a number of tiles/size of tiles that are given by the user
- createimage: creates a new checkerboard image with a user's chosen colors and chosen number of tiles/size of tiles based on user input
- filter: filters a given image and adds it to the list of layers using a filter based on user input
- NOTE: if the user inputs an invalid layer name, image, etc. an error message will appear.

## Example of How to Use Each Command and Order
```
create first
create second
create third
current second
load res/Checkerboard.ppm # loads it in as layer second
blur
sepia
grayscale
save newImage # saves topmost image as the filename given
current third
create fourth
remove fourth
load res/puppy.ppm
sharpen
grayscale
loadall res/correct/layerInfo.txt # loads in new layers based on the layer information provided in the text file
invisible third # makes third layer invisible, so the current will default to be second instead
createdefaultimage 1 2 # creates a 2x2 checkerboard image with tiles of size 1
createimage 1 2 3 4 5 100 244 10 # creates a 2x2 checkerboard image with tiles of size 1 and custom colors
visible third
colortransform res/puppy.ppm sepia # performs sepia on puppy image and adds to layers
filter res/puppy.ppm blur # performs blur on puppy image and adds to layers
saveall newDir #saves all the images in a directory named newDir
```

## Syntax
Here's the syntax necessary to understand how to input commands:
```
- Create a New Layer: create nameOfLayerToCreate
- Make a Layer Current: current nameOfLayerToBecomeCurrentLayer
- Blur an Image in the Current Layer: blur
- Sharpen an Image in the Current Layer: sharpen
- Apply Sepia to an Image in the Current Layer: sepia
- Apply Grayscale to an Image in the Current Layer: grayscale
- Remove a Layer: remove nameOfLayerToBeRemoved
- Load an Image to the Current Layer: load filePathToTheImageToLoadIn
- Load an Image based on a Text File: loadall pathToTextFileWithMultiLayeredImageData
- Save Topmost Visible Image: save nameUserWantsThisImageToBeSavedAs
- Save All Layers: saveall nameUserWantsTheDirectoryThatHoldsTheLayersToBeCalled
- Make Given Layer Invisible: invisible nameOfLayerToBeMadeInvisible
- Make Given Layer Visible: visible nameOfLayerToBeMadeVisible
- Perform Color Transformation on Given Image: colortransform pathToTheImageFile nameOfColorTransformation
- Filter Given Image: filter pathToTheImageFile nameOfFilter
- Create Default Colored Checkerboard: createdefaultimage sizeOfTile numTilesPerRow
- Create User Generated Checkerboard: createdefaultimage sizeOfTile numTilesPerRow firstColorRedValue firstColorGreenValue firstColorBlueValue secondColorRedValue secondColorGreenValue secondColorBlueValue
```