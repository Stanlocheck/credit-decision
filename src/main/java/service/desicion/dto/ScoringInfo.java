package service.desicion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoringInfo {
    private CreditRequest creditRequest;
    private AntifraudResult antifraudResult;
    private BkiResult bkiResult;
    private FsspResult fsspCheckResponse;
    private MobileOperatorResult mobileOperatorResponse;
    private PdnResult pdnResponse;
}
