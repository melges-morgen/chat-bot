package ru.frtk.das;

import com.diffplug.common.base.DurianPlugins;
import com.diffplug.common.base.Errors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DasApplication {
    private static Logger logger = LogManager.getLogger(DasApplication.class);

    public static void main(String[] args) {
        DurianPlugins.register(Errors.Plugins.Log.class, error -> logger.error("Unhandled error", error));
        SpringApplication.run(DasApplication.class, args);
    }
}
