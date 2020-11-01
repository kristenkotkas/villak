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
public class FastMoneyAnswer implements Serializable {
    private static final long serialVersionUID = -4014337036724456368L;
    private long id;
    private String answer;
    private long score;
}
