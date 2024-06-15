package com.farabbit.threaddemo.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Component
@RequiredArgsConstructor
class PointsCalculator {

    private final ExecutorService pointsExecutor;

    CompletableFuture<Integer> pointsOf(Integer playerId) {
        return CompletableFuture
                .supplyAsync(() -> {
                    Sleeper.sleep(Duration.ofMillis(100));
                    return 42;
                }, pointsExecutor);
    }

}
