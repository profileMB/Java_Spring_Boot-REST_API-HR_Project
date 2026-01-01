package com.projects.hrproject.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import jakarta.annotation.PreDestroy;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;


/**
 * Important!!
 * This project is a public demo. To ensure the database remains "clean" and free from inappropriate changes,
 * this class handles the automatic reset of the database after write operations.
 */


@Component
public class DatabaseResetTask {

    private static final Logger log =
            LoggerFactory.getLogger(DatabaseResetTask.class);

    private final DataSource dataSource;

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> scheduledReset;

    public DatabaseResetTask(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Schedules a database reset one hour after the last write operation.
     * Each new write cancels the previous scheduled reset.
     */
    public synchronized void scheduleReset() {

        // Check if a database reset is already scheduled and not yet executed
        if (scheduledReset != null && !scheduledReset.isDone()) {
            scheduledReset.cancel(false);
            log.info("Previous database reset cancelled");
        }

        // Schedule a new database reset to run in one hour
        scheduledReset = scheduler.schedule(
                this::resetDatabase,
                1,
                TimeUnit.HOURS
        );

        log.info("Database reset scheduled in 1 hour");
    }

    // Executes the SQL script to restore the database to its initial state
    private void resetDatabase() {

        Resource resource = new ClassPathResource("data.sql");

        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, resource);
            log.info("Database reset completed");
        } catch (SQLException e) {
            log.error("Database reset failed", e);
        }
    }

    // Stops the scheduler when the application shuts down
    @PreDestroy
    public void shutdown() {
        scheduler.shutdown();
        log.info("Database reset scheduler stopped");
    }
}