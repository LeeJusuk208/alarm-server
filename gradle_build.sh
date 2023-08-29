# make gradle zip files
# zip -s 0 ./alarm-service/gradle/wrapper/gradle-8.1.1.zip --out ./alarm-service/gradle/wrapper/gradle-8.1.1-bin.zip

# build stage
cd ./alarm-service
./gradlew clean build

# clear temp files 
cd ..
# rm ./alarm-service/gradle/wrapper/gradle-8.1.1-bin.zip