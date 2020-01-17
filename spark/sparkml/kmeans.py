import sys
print sys.argv
return(0)
from pyspark.ml.clustering import KMeans
from pyspark.ml import Pipeline
from pyspark.ml import PipelineModel
from pyspark.sql import Row
from pyspark.ml.feature import VectorAssembler
from pyspark.sql.functions import split, col

#Load the data from the previously created devicestatus_etl directory and create a DataFrame that #has 2 columns named Lat and Lon from the 4th and 5th fields in the file
filename = "/devsh_loudacre/devicestatus_etl/*"
latLonDF = spark.read.csv(filename).\
select(col('_c3').cast('float').alias('lat'),\
col('_c4').cast('float').alias('lon'))\
.where("lat <> 0 and lon <> 0")


#Create a vector assembler that will take in our DataFrame and convert the inputCols specified
va = VectorAssembler(inputCols=["lat","lon"],outputCol="features")

#Use the vector assembler to transform the DataFame which will add a new column called 'features' which will be of the Vector type
vectorDF = va.transform(latLonDF)

#Create a Kmeans estimator that takes the "features" column as input and set the value for K to 5 with a tolerance of .01 and a seed # of 12345
km= KMeans(k=5,tol=.01,seed=12345, featuresCol="features")

kmModel = km.fit(vectorDF)

#Print out the cluster centers
for center in kmModel.clusterCenters(): print center
predictionDF = kmModel.transform(vectorDF)
predictionDF.show()

#Same process via an ML pipeline
pl = Pipeline(stages=[va,km])
plmodel = pl.fit(latLonDF)
predictions = plmodel.transform(latLonDF)
plmodel.write().overwrite().save("/loudacre/pipelineModel/")
plmodel1 = PipelineModel.load("/loudacre/pipelineModel/")
predictions.show(5)

