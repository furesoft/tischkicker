package de.shgruppe.tischkicker_server.repositories;

import org.hibernate.annotations.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tischkicker.models.Spieler;

@Repository
@Table(appliesTo = "Spieler")
public interface SpielerRepository extends JpaRepository<Spieler, Integer> {

}