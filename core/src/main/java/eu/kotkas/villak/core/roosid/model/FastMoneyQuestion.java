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
public class FastMoneyQuestion implements Serializable {
    private static final long serialVersionUID = 5072484868659382868L;
    private long id;
    private String question;
    private List<FastMoneyAnswer> answers;
    private FastMoneyPlayerResponse firstPlayer;
    private FastMoneyPlayerResponse secondPlayer;
}
