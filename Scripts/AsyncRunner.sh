#!/usr/bin/env bash


# example of how to run a normal benchmark
#java -cp benchmarks/Java/benchmarks.jar Harness Towers 1 1

# you might need to set these on first time use
# sudo sysctl kernel.perf_event_paranoid=1
# sudo sysctl kernel.kptr_restrict=0

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=Bounce10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness Bounce 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=CD10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness CD 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=DeltaBlue10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness DeltaBlue 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=Havlak10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness Havlak 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=Json10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness Json 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=List10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness List 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=Mandelbrot10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness Mandelbrot 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=NBody10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness NBody 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=Permute10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness Permute 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=Queens10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness Queens 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=Richards10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness Richards 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=Sieve10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness Sieve 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=Storage10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness Storage 1000 1000

java -agentpath:Async/build/libasyncProfiler.so=start,event=cpu,interval=10,file=Towers10_1000_1000.txt -cp benchmarks/Java/benchmarks.jar Harness Towers 1000 1000
