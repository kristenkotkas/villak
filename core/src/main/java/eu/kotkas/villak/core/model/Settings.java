package eu.kotkas.villak.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Settings implements Serializable {

  private Long adminDeviceId;
  private Long gameDeviceId;
  private Boolean shouldRefresh;

}
