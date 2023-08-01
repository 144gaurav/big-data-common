package spark;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import supplier.Supplier;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class LongRunningSparkService extends IntervalStreamProcessor<String> implements InfiniteSparkService{

    private final Predicate<LocalDateTime> stopRunningLoop;

    @Builder
    public LongRunningSparkService(
            Optional<Integer> loopInterval,
            Supplier<Stream<String>> inputStreamSupplier,
            Consumer<String> inputConsumer,
            Optional<Integer> stopServiceAFterHours) {
        super(loopInterval, inputStreamSupplier, inputConsumer);
        this.stopRunningLoop = getPredicateLoop(stopServiceAFterHours);
    }

    @Override
    public void execute() {
    super.run();
    }


    private static Predicate<LocalDateTime> getPredicateLoop(Optional<Integer> restartPeriodHours){
        restartPeriodHours.ifPresent(p -> System.out.println("stop running loop " + p.toString()));
        return restartPeriodHours.isPresent() ?
                StopRunningLoop.builder()
                        .restartTime(Clock.systemDefaultZone().instant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                        .build()
                : defaultPredicate -> true;

    }

}

@RequiredArgsConstructor
@Builder
class StopRunningLoop implements Predicate<LocalDateTime>{

    private final LocalDateTime restartTime;

    @Override
    public boolean test(LocalDateTime localDateTime) {
        return false;
    }
}
