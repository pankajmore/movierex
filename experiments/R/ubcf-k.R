library("recommenderlab")
library("ggplot2")
library("parallel")

# Load the dataset
data <- read.table("../../dataset/u.data")[,-4]

#Convert to realRatingMatrix
mat <- as(data,"realRatingMatrix")

e <- evaluationScheme(mat, method="split",
                            train = 0.8, k=1, given=3)


ubcf <- function(k) {
    r <- Recommender(getData(e,"train"),method="UBCF",parameter=list(nn=k))

    p <- predict(r,getData(e,"known"),type="ratings")

    err <- calcPredictionError(p,getData(e,"unknown"));
    err
    return (err)
}

chooseSim <- function(sim) {
    r <- Recommender(getData(e,"train"),method="UBCF",parameter=list(nn=25,method=sim))

    p <- predict(r,getData(e,"known"),type="ratings")

    err <- calcPredictionError(p,getData(e,"unknown"));
    err
    return (err)
}

chooseSim("cosine")

x<- c(10:500)
y <- mclapply(x,ubcf,mc.preschedule=TRUE,mc.cores=8)

mae <- sapply(y,'[',1)
mse <- sapply(y,'[',2)
rmse <- sapply(y,'[',3)

plot(x,rmse,"l")

plot(x[1:30],rmse[1:30],"l",main="UBCF vs k using Cosine sim.",xlab="k",ylab="RMSE")
plot(x[1:30],mse[1:30],"l",main="UBCF vs k using Cosine sim.",xlab="k",ylab="MSE")
plot(x[1:30],mae[1:30],"l",main="UBCF vs k using Cosine sim.",xlab="k",ylab="MAE")
