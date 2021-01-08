package rw.momo.api.momoapi.sevice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import rw.momo.api.momoapi.model.BalanceResponse;
import rw.momo.api.momoapi.model.TokenResponse;
import rw.momo.api.momoapi.model.PayRequest;

public interface IMomoService {
    TokenResponse getToken();

    BalanceResponse getBalance();
    
    ResponseEntity<Map<String, String>> requestToPay(PayRequest payRequest);

	ResponseEntity<?> requestStatus(String referenceId);
}
