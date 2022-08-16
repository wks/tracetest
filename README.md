A microbenchmark for measuring the time for the garbage collector to perform
marking.

The difference between this and GCBench is that GCBench measures the mutator
time as well, while this test focuses on GC time.  More specifically, it
measures the tracing part because it does not generate much garbage in the
steady state, and the measured time should be dominated by tracing.
