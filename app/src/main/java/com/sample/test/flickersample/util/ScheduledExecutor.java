package com.sample.test.flickersample.util;

import android.support.annotation.NonNull;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A wrapper around {@link Executor} that throttle tasks
 */
public class ScheduledExecutor implements Executor {
    private static final int DELAY_MS = 300;
    private final Timer timer = new Timer();
    private final Executor executor;
    private TimerTask timerTask;

    public ScheduledExecutor() {
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void execute(@NonNull final Runnable runnable) {
        if (timerTask != null) {
            timerTask.cancel();
        }

        timerTask = new TimerTask() {
            @Override
            public void run() {
                executor.execute(runnable);
            }
        };

        timer.schedule(
                timerTask,
                DELAY_MS
        );
    }
}
