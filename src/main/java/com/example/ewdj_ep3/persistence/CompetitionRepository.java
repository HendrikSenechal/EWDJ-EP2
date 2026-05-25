package com.example.ewdj_ep3.persistence;

import com.example.ewdj_ep3.domain.competition.Competition;
import com.example.ewdj_ep3.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Optional<Role> findByName(String name);
}