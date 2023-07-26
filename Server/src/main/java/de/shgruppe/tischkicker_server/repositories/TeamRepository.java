package de.shgruppe.tischkicker_server.repositories;

import org.hibernate.annotations.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tischkicker.models.Team;
@Repository
@Table(appliesTo = "Team")
public interface TeamRepository extends JpaRepository<Team, Integer> {

}