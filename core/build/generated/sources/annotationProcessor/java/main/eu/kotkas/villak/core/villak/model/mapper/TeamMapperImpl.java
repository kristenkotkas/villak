package eu.kotkas.villak.core.villak.model.mapper;

import eu.kotkas.villak.core.villak.model.Team;
import eu.kotkas.villak.core.villak.model.dto.TeamDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-11-08T10:22:40+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_251 (Oracle Corporation)"
)
@Component
public class TeamMapperImpl implements TeamMapper {

    @Override
    public TeamDto convert(Team team) {
        if ( team == null ) {
            return null;
        }

        TeamDto teamDto = new TeamDto();

        teamDto.setName( team.getName() );

        return teamDto;
    }

    @Override
    public Team invert(TeamDto teamDto) {
        if ( teamDto == null ) {
            return null;
        }

        Team team = new Team();

        team.setName( teamDto.getName() );

        return team;
    }
}
