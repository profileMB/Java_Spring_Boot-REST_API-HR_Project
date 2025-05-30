package com.projects.hrproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

/**
 * Important!!
 * This project is a public demo. To ensure the database remains "clean" and free from inappropriate changes,
 * this class handles the automatic reset of the database.
 * Every time an employee is created, updated, or deleted (see the EmployeeController class for more details),
 * a task is scheduled to reset the database to its original state after 1 hour, using the SQL script (data.sql) to restore it.
 */



@Component
public class DatabaseResetTask {

    @Autowired
    private DataSource dataSource;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void scheduleReset() {
    	// Schedule the database reset to occur in 1 hour
    	scheduler.schedule(this::resetDatabase, 1, TimeUnit.HOURS);
    	System.out.println("Database reset scheduled in 1 hour");
    }

    public void resetDatabase() {
        Resource resource = new ClassPathResource("data.sql");
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, resource);
            System.out.println("Database reset completed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}