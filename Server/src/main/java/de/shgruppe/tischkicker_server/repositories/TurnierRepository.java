package de.shgruppe.tischkicker_server.repositories;

import org.hibernate.annotations.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tischkicker.models.Turnier;

import java.util.Date;
import java.util.List;

@Repository
@Table(appliesTo = "Turnier")
public interface TurnierRepository extends JpaRepository<Turnier, Integer> {
   // Turnier findByDate(Date startDatum);
}