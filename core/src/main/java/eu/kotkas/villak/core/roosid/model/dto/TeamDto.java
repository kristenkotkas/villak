package eu.kotkas.villak.core.roosid.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto implements Serializable {

    private static final long serialVersionUID = 6957958845367274546L;

    private String name;

}
