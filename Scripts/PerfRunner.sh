#!/usr/bin/env bash


# example of how to run a normal benchmark
#java -cp benchmarks/Java/benchmarks.jar Harness Towers 1 1

# to report 
#perf report --stdio 
#perf report --stdio -i Bounce.data

perf record -o Dumps/Perf/Bounce.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness Bounce 1000 1000

perf record -o Dumps/Perf/CD.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness CD 1000 1000

perf record -o Dumps/Perf/DeltaBlue.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness DeltaBlue 1000 1000

perf record -o Dumps/Perf/Havlak.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness Havlak 1000 15000

perf record -o Dumps/Perf/Json.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness Json 1000 1000

perf record -o Dumps/Perf/List.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness List 1000 1000

perf record -o Dumps/Perf/Mandelbrot.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness Mandelbrot 1000 500

perf record -o Dumps/Perf/NBody.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness NBody 1000 250000

perf record -o Dumps/Perf/Permute.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness Permute 1000 1000

perf record -o Dumps/Perf/Queens.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness Queens 1000 1000

perf record -o Dumps/Perf/Richards.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness Richards 1000 1000

perf record -o Dumps/Perf/Sieve.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness Sieve 1000 1000

perf record -o Dumps/Perf/Storage.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness Storage 1000 1000

perf record -o Dumps/Perf/Towers.data -- java -XX:+UnlockDiagnosticVMOptions -XX:+DumpPerfMapAtExit -cp benchmarks/Java/benchmarks.jar Harness Towers 1000 1000
