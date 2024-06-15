package com.farabbit.threaddemo.graphql;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

class Sleeper {

    static void sleep(Duration duration) {
        try {
            TimeUnit.MILLISECONDS.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
