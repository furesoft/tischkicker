package de.shgruppe.tischkicker_server;

import org.hibernate.annotations.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(appliesTo = "Spieler")
public interface SpielerRepository extends JpaRepository<Spieler, Integer> {

}