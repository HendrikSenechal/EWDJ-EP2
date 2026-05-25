package com.example.ewdj_ep3.domain.prediction;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PredictionId implements Serializable {
    private Long userId;
    private Long matchId;
}