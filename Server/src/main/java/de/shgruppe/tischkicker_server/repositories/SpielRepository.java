package de.shgruppe.tischkicker_server.repositories;

import org.hibernate.annotations.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tischkicker.models.Spiel;

@Repository
@Table(appliesTo = "Spiel")
public interface SpielRepository extends JpaRepository<Spiel, Integer> {

}