package com.farabbit.threaddemo.graphql;

import com.devskiller.jfairy.Fairy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Log4j2
@Component
@RequiredArgsConstructor
class InventoryClient {

    private final Fairy fairy;
    private final ExecutorService inventoryExecutor;

    CompletableFuture<List<Item>> loadInventory(Integer playerId) {

        log.info("-----------, loadInventory playerId = "+playerId);

        return CompletableFuture.supplyAsync(() -> {
            if(playerId == 11){
                Sleeper.sleep(Duration.ofMillis(100));
            }else{
                Sleeper.sleep(Duration.ofMillis(600));
            }

            return Arrays.asList(new Item(fairy.baseProducer().randomElement("Sword", "Shield", "Shoes", "Spell", "Potion")),
                    new Item(fairy.baseProducer().randomElement("Sword", "Shield", "Shoes", "Spell", "Potion")));
            //return List.of();
        }, inventoryExecutor);
    }

}
