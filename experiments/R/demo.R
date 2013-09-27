library("recommenderlab")
library("ggplot2")

data(MovieLense)
image(sample(MovieLense, 500), main = "Raw ratings")

# Visualizing ratings
qplot(getRatings(MovieLense), binwidth = 1, 
      main = "Histogram of ratings", xlab = "Rating")
summary(getRatings(MovieLense)) # Skewed to the right
# Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
# 1.00    3.00    4.00    3.53    4.00    5.00

recommenderRegistry$get_entries(dataType = "realRatingMatrix")
# We have a few options

# Let's check some algorithms against each other
scheme <- evaluationScheme(MovieLense, method = "split", train = .9,
                           k = 1, given = 10, goodRating = 0)

scheme

algorithms <- list(
  "random items" = list(name="RANDOM", param=list(normalize = "Z-score")),
  "popular items" = list(name="POPULAR", param=list(normalize = "Z-score")),
  "user-based CF" = list(name="UBCF", param=list(normalize = "Z-score",
                                                 method="Cosine",
                                                 nn=50, minRating=3)),
  "item-based CF" = list(name="IBCF2", param=list(normalize = "Z-score"
                                                 ))
  
  )

# run algorithms, predict next n movies
results <- evaluate(scheme, algorithms, n=c(1, 3, 5, 10, 15, 20))

# Draw ROC curve
plot(results, annotate = 1:4, legend="topleft")

# See precision / recall
plot(results, "prec/rec", annotate=3)

r <- Recommender(getData(scheme,"train"), "UBCF")
p <- predict(r,getData(scheme,"known"),type="ratings")
calcPredictionError(p,getData(scheme,"unknown"))

