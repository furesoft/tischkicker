package de.shgruppe.tischkicker_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("tischkicker.models")
@EnableJpaRepositories("de.shgruppe")
public class TischKickerEntry {

    public static void main(String[] args) {
        SpringApplication.run(TischKickerEntry.class, args);
    }

}
