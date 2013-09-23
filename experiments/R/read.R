library("recommenderlab")
library("ggplot2")

# Load the dataset
ua.base <- read.table("ua.base")[,-4]
ua.test <- read.table("ua.test")[,-4]

#Convert to realRatingMatrix
train <- as(ua.base,"realRatingMatrix")
test <- as(ua.test,"realRatingMatrix")

#Correct the dimensions of test
a <-  matrix(NA, nrow=943, ncol=1680)
a[,1:1129] <- as(test,"matrix")
test1 <- as(a,"realRatingMatrix")

ubcf <- Recommender(train,method="UBCF")

p <- predict(ubcf,test1,type="ratings")

err <- calcPredictionError(p,test1)
