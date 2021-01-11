package rw.momo.api.momoapi.sevice;

import org.springframework.http.ResponseEntity;

import rw.momo.api.momoapi.model.BalanceResponse;
import rw.momo.api.momoapi.model.PayRequest;
import rw.momo.api.momoapi.model.PayResponse;
import rw.momo.api.momoapi.model.TokenResponse;

public interface IMomoService {
    TokenResponse getToken();

    BalanceResponse getBalance();
    
    ResponseEntity<PayResponse> requestToPay(PayRequest payRequest);

	ResponseEntity<?> requestStatus(String referenceId);
}
