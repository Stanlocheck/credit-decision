package service.desicion.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreditRequest {
    //private UserInfo userInfo;

    private Integer creditAmount;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer percent;
    private Integer payment;
}
