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

    public void decision(ScoringInfo scoringInfo) {

        Credit credit = creditRepository.findById(scoringInfo.getCreditId()).orElse(null);
        if (credit == null) {
            throw new RuntimeException("Request not found");
        }

        int antifraudResult = checkAntifraud(scoringInfo.getAntifraudResult());
        if (antifraudResult == 0) {
            credit.setStatus(Status.REJECT);
            creditRepository.save(credit);
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

    public int checkAntifraud(AntifraudResult antifraudResult){
        if (antifraudResult != null && (antifraudResult.getIsFraud() || antifraudResult.getIsBlackList())) {
            return 0;
        } else if (antifraudResult.getFraudScore() <= 100){
            return antifraudResult.getFraudScore();
        } else {
            return 20;
        }
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
        if(fsspResult != null){
            if(fsspResult.getTotalLoansAmount() < 500000 && fsspResult.getTotalLoansAmount() > 100000){
                return 30;
            } else if(fsspResult.getTotalLoansAmount() < 100000){
                return 60;
            } else {
                return -50;
            }
        }

        return 0;
    }

    public Integer checkPdn(PdnResult pdnResult){
        if(pdnResult != null){
            if(pdnResult.getPdnValue() < 0.3){
                return 30;
            } else if(pdnResult.getPdnValue() < 0.5){
                return 15;
            } else {
                return -20;
            }
        }

        return 0;
    }

    public Integer checkMobileOperator(MobileOperatorResult mobileOperatorResult){
        if(mobileOperatorResult != null){
            return mobileOperatorResult.getScore();
        }

        return 0;
    }
}
