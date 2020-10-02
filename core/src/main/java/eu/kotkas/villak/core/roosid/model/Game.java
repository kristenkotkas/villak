package eu.kotkas.villak.core.roosid.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game implements Serializable {
    private static final long serialVersionUID = 5911818142075747766L;
    private List<Team> teams;
    private List<Round> rounds;
    private List<Message> latestMessages;
}
