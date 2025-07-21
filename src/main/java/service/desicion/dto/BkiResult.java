package service.desicion.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BkiResult {
    private Boolean hasOverstays;
    private Boolean hasOpenLoans;
    private List<Loan> loans;
}
