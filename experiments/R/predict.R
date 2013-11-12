library("recommenderlab")
library("ggplot2")
library("parallel")

# Load the dataset
data <- read.table("../../dataset/u.data")[,-4]

#Convert to realRatingMatrix
mat <- as(data,"realRatingMatrix")

e <- evaluationScheme(mat, method="split",
                      train = 0.8, k=1, given=3)

e <- evaluationScheme(mat, method="cross-validation",
                      given=3)
train <- getData(e,"train")
known <- getData(e,"known")
unknown <- getData(e,"unknown")

r <- Recommender(mat,method="UBCF",parameter=list(nn=100))
p <- predict(r,mat,"ratings")

top <- predict(r,mat,n=10)
topL <- as(p,"list")


