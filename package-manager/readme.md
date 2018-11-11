# CodeChallenge


[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Steps to run the application
  - clone the project from the repository
  - build the project mvn clean install
  - cd target
  - Run the jar java -jar package-manager-0.0.1-SNAPSHOT-jar-with-dependencies.jar            -mobiquity.package.file "Path to the data file"

# Patterns Used

  - OptimalPackageRecordReader has been created using iterator design pattern
  - Iterator,Strategy(but only single strategy can be seen with this design as of now),Singleton has been used
  - TranformedIterator has been designed which takes Iterator,a record processor,record reader as input and transforms the record
