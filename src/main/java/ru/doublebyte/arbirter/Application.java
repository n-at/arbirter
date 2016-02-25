package ru.doublebyte.arbirter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        createDirectories();
        SpringApplication.run(Application.class, args);
    }

    /**
     * Create application directories (if not exist)
     */
    public static void createDirectories() {
        try {
            Path publicPath = Paths.get("public");
            if(!Files.exists(publicPath)) {
                logger.info("Creating public directory...");
                Files.createDirectory(publicPath);
            }

            Path logsPath = Paths.get("logs");
            if(!Files.exists(logsPath)) {
                logger.info("Creating logs directory...");
                Files.createDirectory(logsPath);
            }
        } catch(Exception e) {
            logger.error("Cannot create directories", e);
            System.exit(1);
        }
    }

}
