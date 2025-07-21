package service.desicion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FsspResult {
    private Boolean hasOpenLoans;
    private Integer totalLoansAmount;
}
