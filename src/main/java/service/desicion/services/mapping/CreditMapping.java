package service.desicion.services.mapping;

import org.springframework.stereotype.Service;
import service.desicion.dto.CreditRequest;
import service.desicion.entities.Credit;

@Service
public class CreditMapping {
    public Credit toEntity(CreditRequest creditRequest){

        Credit credit = new Credit();
        credit.setCreditAmount(creditRequest.getCreditAmount());
        credit.setType(creditRequest.getType());
        credit.setStartDate(creditRequest.getStartDate());
        credit.setEndDate(creditRequest.getEndDate());
        credit.setPercent(creditRequest.getPercent());
        credit.setPayment(creditRequest.getPayment());

        return credit;
    }
}
