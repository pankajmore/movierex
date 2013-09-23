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
    r <- Recommender(getData(e,"train"),method="UBCF",parameter=list(nn=k))

    p <- predict(r,getData(e,"known"),type="ratings")

    err <- calcPredictionError(p,getData(e,"unknown"));
    err
    return (err)
}

x<- c(10:500)
y <- mclapply(x,run,mc.preschedule=TRUE,mc.cores=8)

mae <- sapply(y,'[',1)
mse <- sapply(y,'[',2)
rmse <- sapply(y,'[',3)

plot(x,rmse,"l")

plot(x[1:40],rmse[1:40],"l",main="UBCF vs k using Cosine sim.",xlab="k",ylab="RMSE")
plot(x[1:40],mse[1:40],"l",main="UBCF vs k using Cosine sim.",xlab="k",ylab="MSE")
plot(x[1:40],mae[1:40],"l",main="UBCF vs k using Cosine sim.",xlab="k",ylab="MAE")
