package de.shgruppe.tischkicker_server;

import de.shgruppe.tischkicker_server.repositories.SpielRepository;
import de.shgruppe.tischkicker_server.repositories.SpielerRepository;
import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import de.shgruppe.tischkicker_server.repositories.TurnierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tischkicker.models.Spiel;
import tischkicker.models.Spieler;
import tischkicker.models.Team;
import tischkicker.models.Turnier;

//test

@SpringBootApplication
@EntityScan("tischkicker.models")
@EnableJpaRepositories("de.shgruppe.tischkicker_server.repositories")
public class TischKickerEntry {
    public static void main(String[] args) {
        SpringApplication.run(TischKickerEntry.class, args);
    }
    @Bean
    CommandLineRunner init (TurnierRepository turnierRepository)
    {
        return args -> {
            if (turnierRepository.findById(1).isEmpty()) {
            Turnier turnier = new Turnier();
            turnier.setStartdatum("01.01.1900");
            turnier.setEnddatum("01.01.1901");
            turnier.setMaximalToreBisGewonnen(10);
            turnier.setTordifferenz(0);
            turnier.setGespielt(false);
            turnier.setSpiele("-1,-1");
            turnier.setTeams("1,2");
            turnier.setTurnierName("Quickplay");
            turnierRepository.saveAndFlush(turnier);
        }
        };
    }
    @Bean
    CommandLineRunner init1 (SpielRepository spielRepository)
    {
        return args -> {
            if(spielRepository.findById(1).isEmpty()) {
                Spiel spiel = new Spiel();
                spiel.setTeams("1,2");
                spiel.setToreteam2(0);
                spiel.setToreteam1(0);
                spiel.setQualifikation(1);
                spiel.setSpiele("-1,-1");
                spiel.setTurnierID(1);
                spiel.setGewinnerID(-1);
                spielRepository.saveAndFlush(spiel);
            }
        };
    }
    @Bean
    CommandLineRunner init2 (TeamRepository teamRepository)
    {
        return args -> {
            if(teamRepository.findById(1).isEmpty()) {
                Team team1 = new Team();
                team1.setName("A");
                team1.setSpieler("1");
                team1.setGesamttore(0);
                team1.setGegentore(0);
                team1.setAufgegeben(false);
                teamRepository.saveAndFlush(team1);
                Team team2 = new Team();
                team2.setName("B");
                team2.setSpieler("2");
                team2.setGesamttore(0);
                team2.setGegentore(0);
                team2.setAufgegeben(false);
                teamRepository.saveAndFlush(team2);
            }
        };
    }
    @Bean
    CommandLineRunner init3 (SpielerRepository spielerRepository)
    {
        return args -> {
            if(spielerRepository.findById(1).isEmpty()) {
                Spieler spieler1 = new Spieler();
                spieler1.setName("A");
                spielerRepository.saveAndFlush(spieler1);
                Spieler spieler2 = new Spieler();
                spieler2.setName("B");
                spielerRepository.saveAndFlush(spieler2);
            }
        };
    }
}
