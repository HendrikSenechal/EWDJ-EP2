package com.example.ewdj_ep3.persistence;

import com.example.ewdj_ep3.domain.prediction.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
}