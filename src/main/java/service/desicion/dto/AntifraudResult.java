package service.desicion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AntifraudResult {
    private Boolean isFraud;
    private String firstName;
    private String secondName;
    private Boolean isBlackList;
    private Long fraudScore;
}
