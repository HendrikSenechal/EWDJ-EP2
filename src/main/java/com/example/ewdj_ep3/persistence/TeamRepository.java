package com.example.ewdj_ep3.persistence;

import com.example.ewdj_ep3.domain.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}