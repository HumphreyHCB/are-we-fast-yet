#!/usr/bin/env bash


# example of how to run a normal benchmark
#java -cp benchmarks/Java/benchmarks.jar Harness Towers 1 1


java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/Bounce.jfr -cp benchmarks/Java/benchmarks.jar Harness Bounce 1000 1000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/CD.jfr  -cp benchmarks/Java/benchmarks.jar Harness CD 1000 1000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/DeltaBlue.jfr  -cp benchmarks/Java/benchmarks.jar Harness DeltaBlue 1000 1000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/Havlak.jfr  -cp benchmarks/Java/benchmarks.jar Harness Havlak 1000 15000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/Json.jfr  -cp benchmarks/Java/benchmarks.jar Harness Json 1000 1000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/List.jfr  -cp benchmarks/Java/benchmarks.jar Harness List 1000 1000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/Mandelbrot.jfr  -cp benchmarks/Java/benchmarks.jar Harness Mandelbrot 1000 500

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/NBody.jfr  -cp benchmarks/Java/benchmarks.jar Harness NBody 1000 250000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/Permute.jfr  -cp benchmarks/Java/benchmarks.jar Harness Permute 1000 1000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/Queens.jfr  -cp benchmarks/Java/benchmarks.jar Harness Queens 1000 1000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/Richards.jfr  -cp benchmarks/Java/benchmarks.jar Harness Richards 1000 1000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/Sieve.jfr  -cp benchmarks/Java/benchmarks.jar Harness Sieve 1000 1000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/Storage.jfr  -cp benchmarks/Java/benchmarks.jar Harness Storage 1000 1000

java -XX:StartFlightRecording=settings=profile,filename=Dumps/JFR/Towers.jfr  -cp benchmarks/Java/benchmarks.jar Harness Towers 1000 1000
