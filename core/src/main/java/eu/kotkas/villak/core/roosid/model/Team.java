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
public class Team implements Serializable {
    private static final long serialVersionUID = 8990040539420759198L;
    private long id;
    private String name;
    private long score;
    private long crossCount;
}
