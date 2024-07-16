# AvgTemperatures application

## Several comments and information from me:

- I had an idea to write a Micronaut or Quarkus application, but I have decided that a combination of Spring Boot and Java will be the best common stack everyone understands.
- I have used OpenCSV, while being completely aware of https://devhub.checkmarx.com/cve-details/Cx78f40514-81ff/, but I still am an advocate of OpenCSV. But finally, I have implemented tbe same logic without using OpenCSV.
- I have assumed that the CSV file may contain corrupt data, and this case is (hopefully fully) covered.
- I have thought of H2 to store the temperatures information, but finally, the task is about working with large files, not databases ;)
- I could sit and develop this application for a couple of days, but I have decided that I'd focus on the requirements, without developing additional functionalities.
- The optimisation of large file reading is actually best is the best thing I could think of.
- There are not many tests, as I don't see many edge cases
- Aside from the CSV file reading logic, I have kept the rest of the code as simple as possible, as I am a big fan of KISS and DRY 

## Running and testing the application:

`./gradlew run` will start the application. 

There are two endpoints:

`localhost:8080/api/temperatures?city=Wrocław`
`localhost:8080/api/temperatures/noopencsv?city=Wrocław`

# I wish You a wonderful day!