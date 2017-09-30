// Misc. functions on Row objects
val jsonSeq = Seq("""{ "firstName":"Nate", "iq":0, "prev_iq":200}""",
              """{ "firstName":"Jackie", "iq":200, "prev_iq":200 }""",
              """{ "firstName":"Ricky", "iq":-20}""")
val peopleDS = spark.createDataset(jsonSeq)

val peopleDF = spark.read.json(peopleDS)
val firstRow = peopleDF.take(3)(2)

println(firstRow.getClass)

// println(firstRow.getAs[String]("firstName"))
val firstName = firstRow.getAs[String]("firstName")
val iq = firstRow.getAs[Long]("iq")
println(firstName)
println(firstName.getClass)
println(iq)
println(iq.getClass)
