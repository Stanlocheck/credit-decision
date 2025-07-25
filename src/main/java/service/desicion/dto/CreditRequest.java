package service.desicion.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreditRequest {
    private Integer creditAmount;
    private String type;
    private String currency;
    private Integer inMonths;
}
