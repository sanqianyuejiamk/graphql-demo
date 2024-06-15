package com.farabbit.threaddemo.graphql;

import com.devskiller.jfairy.Fairy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Component
@RequiredArgsConstructor
class PlayerMetadata {

    private final ExecutorService playerExecutor;

    private final Fairy fairy;

    CompletableFuture<String> lookupName(Integer playerId) {
        return CompletableFuture.supplyAsync(() -> {
            Sleeper.sleep(Duration.ofMillis(100));
            return fairy.person().getFirstName()+"_id_"+playerId;
        }, playerExecutor);
    }
}
