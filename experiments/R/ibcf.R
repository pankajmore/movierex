library("recommenderlab")
library("ggplot2")
library("parallel")

# Load the dataset
data <- read.table("../../dataset/u.data")[,-4]

#Convert to realRatingMatrix
mat <- as(data,"realRatingMatrix")

e <- evaluationScheme(mat, method="split",
                            train = 0.8, k=1, given=3)


run <- function(k) {
    r <- Recommender(getData(e,"train"),method="POPULAR")

    p <- predict(r,getData(e,"known"),type="ratings")

    err <- calcPredictionError(p,getData(e,"unknown"));
    err
    return (err[[3]])
}

run(10)


x<- c(10:500)
y <- mclapply(x,run,mc.preschedule=TRUE,mc.cores=8)
plot(x,y,"l")
plot(x[1:40],y[1:40],"l",main="UBCF vs k",xlab="k",ylab="RMSE")
