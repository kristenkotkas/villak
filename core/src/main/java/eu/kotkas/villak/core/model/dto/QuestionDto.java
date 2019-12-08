package eu.kotkas.villak.core.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import eu.kotkas.villak.core.Main;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "amount",
  "question",
  "answer",
  "pictureUri",
  "soundUri",
  "silver"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto implements Serializable {

  private final static long serialVersionUID = -1600523776769659944L;

  private Integer amount;
  private String question;
  private String answer;
  private String pictureUri;
  private String soundUri;
  private Boolean silver;

  public void setPictureUri(String pictureUri) {
    this.pictureUri = String.format("http://localhost:%s/files/%s", Main.getPort(), pictureUri);
  }

  public void setSoundUri(String soundUri) {
    this.soundUri = String.format("http://localhost:%s/files/%s", Main.getPort(), soundUri);
  }
}
