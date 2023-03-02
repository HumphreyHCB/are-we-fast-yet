#!/usr/bin/env bash


# example of how to run a normal benchmark
#java -cp benchmarks/Java/benchmarks.jar Harness Towers 1 1


java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=bounce -cp benchmarks/Java/benchmarks.jar Harness Bounce 1000 1000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=CD -cp benchmarks/Java/benchmarks.jar Harness CD 1000 1000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=DeltaBlue -cp benchmarks/Java/benchmarks.jar Harness DeltaBlue 1000 1000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=Havlak -cp benchmarks/Java/benchmarks.jar Harness Havlak 1000 15000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=Json -cp benchmarks/Java/benchmarks.jar Harness Json 1000 1000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=List -cp benchmarks/Java/benchmarks.jar Harness List 1000 1000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=Mandelbrot -cp benchmarks/Java/benchmarks.jar Harness Mandelbrot 1000 500

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=NBody -cp benchmarks/Java/benchmarks.jar Harness NBody 1000 250000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=Permute -cp benchmarks/Java/benchmarks.jar Harness Permute 1000 1000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=Queens -cp benchmarks/Java/benchmarks.jar Harness Queens 1000 1000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=Richards -cp benchmarks/Java/benchmarks.jar Harness Richards 1000 1000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=Sieve -cp benchmarks/Java/benchmarks.jar Harness Sieve 1000 1000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=Storage -cp benchmarks/Java/benchmarks.jar Harness Storage 1000 1000

java -agentpath:YourKit/libyjpagent.so=onexit=snapshot,dir=Dumps/YourKit,async_sampling_cpu,sessionname=Towers -cp benchmarks/Java/benchmarks.jar Harness Towers 1000 1000
