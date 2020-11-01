package eu.kotkas.villak.core.roosid.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FastMoney implements Serializable {
    private static final long serialVersionUID = 1894704584787301139L;
    private List<FastMoneyQuestion> questions;
    private long currentScore;
    private boolean active;
}
