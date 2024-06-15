package com.farabbit.threaddemo.graphql;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Component
class BillingRepository {

    private final ExecutorService billingExecutor;

    CompletableFuture<Billing> forUser(Integer playerId) {
        return CompletableFuture.supplyAsync(() -> {
                    Sleeper.sleep(Duration.ofMillis(300));
                    return new Billing(BigDecimal.TEN, ImmutableList.of(
                            new Operation(BigDecimal.TEN, "Item purchase"),
                            new Operation(BigDecimal.ONE, "Item purchase")
                    ));
                }, billingExecutor);
    }

}
