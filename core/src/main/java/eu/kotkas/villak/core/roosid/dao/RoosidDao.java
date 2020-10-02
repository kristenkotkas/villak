package eu.kotkas.villak.core.roosid.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.kotkas.villak.core.Main;
import eu.kotkas.villak.core.roosid.model.Game;
import java.io.File;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Repository
@Getter
@Setter
public class RoosidDao {

    public Game getInitialGame() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(getFilePath()), Game.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveGame(Game game) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(new File(getFilePath()), game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilePath() {
        return Main.getStaticFolderPath() + "/roosid.json";
    }
}
