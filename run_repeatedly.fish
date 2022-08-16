argparse 'i/invocations=' 'g/gcs=' 'd/depth=' 'w/warmups=' -- $argv
or return

set -g invocations $_flag_invocations
set -g gcs $_flag_gcs
set -g depth $_flag_depth
set -g warmups $_flag_warmups

if test (count $invocations) -eq 0
    set invocations 5
end

if test (count $gcs) -eq 0
    set gcs 100
end

if test (count $depth) -eq 0
    set depth 22
end

if test (count $warmups) -eq 0
    set warmups 10
end

echo Run $gcs GCs each time
echo Repeat $invocations time
echo Tree depth: $depth
echo Warm-ups: $warmups

set -g BIN1 /home/wks/projects/mmtk-github/openjdk/build/linux-x86_64-normal-server-release/images/jdk/bin/java
set -g BIN2 /home/wks/projects/mmtk-github/parallels/edge-shape/openjdk/build/linux-x86_64-normal-server-release/images/jdk/bin/java

set -g PLANS SemiSpace Immix GenCopy GenImmix
#set -g PLANS Immix

echo "Cleaning up old data..."
rm -r out

for invocation in (seq $invocations)
    for plan in $PLANS
        set ENVS MMTK_THREADS=1 RUST_BACKTRACE=1 MMTK_PLAN=$plan 
        set COMMON_ARGS -XX:+UseThirdPartyHeap -server -XX:MetaspaceSize=100M -Xm{s,x}500M TraceTest $depth $gcs $warmups
        echo $plan $invocation
        mkdir -p out/$plan
        env $ENVS $BIN1 $COMMON_ARGS >> out/$plan/gctimes-master.log
        env $ENVS $BIN2 $COMMON_ARGS >> out/$plan/gctimes-branch.log
    end
end
