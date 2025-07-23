package service.desicion.services;

import org.springframework.stereotype.Service;
import service.desicion.dto.*;
import service.desicion.entities.Credit;
import service.desicion.entities.Status;
import service.desicion.repositories.CreditRepository;

@Service
public class DecisionService {
    private final CreditRepository creditRepository;

    public DecisionService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    public ScoringResult decision(ScoringInfo scoringInfo) {

        Credit credit = creditRepository.findById(scoringInfo.getCreditId()).orElse(null);
        if (credit == null) {
            throw new RuntimeException("Request not found");
        }

        int totalScore = 0;

        AntifraudResult antifraudResult = scoringInfo.getAntifraudResult();
        BkiResult bkiResult = scoringInfo.getBkiResult();
        FsspResult fsspResult = scoringInfo.getFsspCheckResponse();
        MobileOperatorResult mobileOperatorResult = scoringInfo.getMobileOperatorResponse();
        PdnResult pdnResult = scoringInfo.getPdnResponse();

        //Antifraud
        if (antifraudResult != null && (antifraudResult.getIsFraud() || antifraudResult.getIsBlackList())) {
            return new ScoringResult(false, "Подозрение на мошенничество", 0);
        } else {
            totalScore += antifraudResult.getFraudScore();
        }

        //Bki
        if(bkiResult != null){
            if(!bkiResult.getHasOverstays()){
                totalScore += 100;
            }

            int closedLoans = 0;
            int openLoans = 0;

            if(bkiResult.getLoans() != null){
                for(Loan loan : bkiResult.getLoans()){
                    if (loan == null) continue;

                    if(loan.getIsClosed()){
                        closedLoans++;
                    } else {
                        openLoans++;
                    }
                }
            } else {
                totalScore += 100;
            }

            totalScore += Math.min(closedLoans * 10, 50);

            if(openLoans > 3){
                totalScore -= 20;
            }
        }

        //Fssp
        if(fsspResult != null){
            if(fsspResult.getTotalLoansAmount() < 500000 && fsspResult.getTotalLoansAmount() > 100000){
                totalScore += 30;
            } else if(fsspResult.getTotalLoansAmount() < 100000){
                totalScore += 60;
            } else {
                totalScore -= 50;
            }
        }

        //Mobile operator
        if(mobileOperatorResult != null){
            totalScore += mobileOperatorResult.getScore();
        }

        //Pdn
        if(pdnResult != null){
            if(pdnResult.getPdnValue() < 0.3){
                totalScore += 30;
            } else if(pdnResult.getPdnValue() < 0.5){
                totalScore += 15;
            } else {
                totalScore -= 20;
            }
        }

        //Result
        if (totalScore >= 300) {
            credit.setStatus(Status.APPROVE);
            creditRepository.save(credit);
            return new ScoringResult(true, "Заявка одобрена", totalScore);
        } else if (totalScore >= 200) {
            credit.setStatus(Status.REJECT);
            return new ScoringResult(false, "На ручной скоринг", totalScore);
        } else {
            credit.setStatus(Status.REJECT);
            return new ScoringResult(false, "Низкий скоринг", totalScore);
        }
    }
}
