package service.desicion.services;

import jakarta.persistence.criteria.CriteriaBuilder;
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

    public void decision(ScoringInfo scoringInfo) {

        Credit credit = creditRepository.findById(scoringInfo.getCreditId()).orElse(null);
        if (credit == null) {
            throw new RuntimeException("Request not found");
        }

        int antifraudResult = checkAntifraud(scoringInfo.getAntifraudResult());
        if (antifraudResult == 0) {
            return;
        }
        int bkiResult = checkBki(scoringInfo.getBkiResult());
        int fsspResult = checkFssp(scoringInfo.getFsspCheckResponse());
        int pdnResult = checkPdn(scoringInfo.getPdnResponse());
        int mobileOperatorResult = checkMobileOperator(scoringInfo.getMobileOperatorResponse());

        int totalScore = antifraudResult + bkiResult + fsspResult + pdnResult + mobileOperatorResult;

        //Result
        if (totalScore >= 300) {
            credit.setStatus(Status.APPROVE);
            creditRepository.save(credit);
        } else {
            credit.setStatus(Status.REJECT);
            creditRepository.save(credit);
        }
    }

    public Integer checkAntifraud(AntifraudResult antifraudResult){
        int score = 0;
        if (antifraudResult != null && (antifraudResult.getIsFraud() || antifraudResult.getIsBlackList())) {
            return score;
        } else if (antifraudResult.getFraudScore() <= 100){
            score += antifraudResult.getFraudScore();
        } else {
            score += 20;
        }

        return score;
    }

    public Integer checkBki(BkiResult bkiResult){
        int score = 0;
        if(bkiResult != null){
            if(!bkiResult.getHasOverstays()){
                score += 100;
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
                score += 100;
            }

            score += Math.min(closedLoans * 10, 50);

            if(openLoans > 3){
                score -= 20;
            }
        }

        return score;
    }

    public Integer checkFssp(FsspResult fsspResult){
        int score = 0;
        if(fsspResult != null){
            if(fsspResult.getTotalLoansAmount() < 500000 && fsspResult.getTotalLoansAmount() > 100000){
                score += 30;
            } else if(fsspResult.getTotalLoansAmount() < 100000){
                score += 60;
            } else {
                score -= 50;
            }
        }

        return score;
    }

    public Integer checkPdn(PdnResult pdnResult){
        int score = 0;
        if(pdnResult != null){
            if(pdnResult.getPdnValue() < 0.3){
                score += 30;
            } else if(pdnResult.getPdnValue() < 0.5){
                score += 15;
            } else {
                score -= 20;
            }
        }

        return score;
    }

    public Integer checkMobileOperator(MobileOperatorResult mobileOperatorResult){
        int score = 0;
        if(mobileOperatorResult != null){
            score += mobileOperatorResult.getScore();
        }

        return score;
    }
}
