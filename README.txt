This project is used to create the "image path files" that opencv needs to classify positive images and background images.
The first snippet, in test 1, is to classify positive, trimmed images.
THe second snipper, in test 2, is to classify negative images (backgrounds).

To create a positive sample we use the command;
(since we're using -info instead of -img, opencv ignores variables like maxangle and bgcolor etc)
-info bg2X.txt -num 1002 -bg bg.txt -vec samples.vec -maxxangle 0.3 -maxzangle 0.5 -maxyangle 0.3 -maxidev 30 -bgcolor 0 -bgthresh 10 -w 24 -h 20

bg2X.txt here would contain paths like;
img/imagename.png 1 0 0 128 128
If we had 1 image object from position 0,0 to 128,128 (x,y)

to train it we use the command;
-data haarcascade -vec samples.vec -bg bg.txt -numPos 900 -numNeg 900 -numStages 20 -featureType LBP -w 24 -h 20 -minHitRate 0.999 -maxfalsealarm 0.5 -precalcValBufSize 2048 -precalcIdxBufSize 2048
haarcascade is the folder in which results will be written (each stage is written then the final xml is also written)
bg.txt is a list of all negative images like "img/neg.png"
samples.vec is the vector file created from haartraining + the command explained before this one.
