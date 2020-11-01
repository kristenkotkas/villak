package eu.kotkas.villak.core.roosid.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FastMoneyPlayerResponse implements Serializable {
    private static final long serialVersionUID = 526660036114442465L;
    private long id;
    private String answer;
    private Long score;
    private boolean questionVisible;
}
