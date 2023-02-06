package com.mengka.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
class QueryResolver implements GraphQLQueryResolver {

    private final BillingRepository billingRepository;
    private final InventoryClient inventoryClient;
    private final PlayerMetadata playerMetadata;
    private final PointsCalculator pointsCalculator;

    Player currentPlayer(Integer id) {
        UUID playerId = UUID.randomUUID();
        return new Player(id, billingRepository, inventoryClient, playerMetadata, pointsCalculator);
    }

    List<Player> currentPlayerAll(){
        UUID playerId = UUID.randomUUID();
        UUID playerId2 = UUID.randomUUID();
        return Arrays.asList(new Player(playerId.hashCode(), billingRepository, inventoryClient, playerMetadata, pointsCalculator),
                new Player(playerId2.hashCode(), billingRepository, inventoryClient, playerMetadata, pointsCalculator));
    }
}

@RequiredArgsConstructor
class Player {

    private final Integer id;

    private final BillingRepository billingRepository;
    private final InventoryClient inventoryClient;
    private final PlayerMetadata playerMetadata;
    private final PointsCalculator pointsCalculator;

    CompletableFuture<Billing> billing() {
        return billingRepository.forUser(id);
    }

    CompletableFuture<String> name() {
        return playerMetadata.lookupName(id);
    }

    CompletableFuture<Integer> points() {
        return pointsCalculator.pointsOf(id);
    }

    CompletableFuture<List<Item>> inventory() {
        return inventoryClient.loadInventory(id);
    }
}

@Value
class Item {
    String name;
}

@Value
class Billing {
    BigDecimal balance;
    ImmutableList<Operation> operations;
}

@Value
class Operation {
    BigDecimal amount;
    String description;
}