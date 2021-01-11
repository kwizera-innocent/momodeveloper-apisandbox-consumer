package rw.momo.api.momoapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.momo.api.momoapi.model.BalanceResponse;
import rw.momo.api.momoapi.model.PayRequest;
import rw.momo.api.momoapi.model.TokenResponse;
import rw.momo.api.momoapi.sevice.IMomoService;


@RestController
@RequestMapping("/momo/request")
public class Momo {
    @Autowired
    IMomoService service;

    @GetMapping(value="/token")
    public ResponseEntity<TokenResponse> getToken() {
        return new ResponseEntity<TokenResponse>(service.getToken(), HttpStatus.OK);
    }

    @GetMapping(value="/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        return new ResponseEntity<BalanceResponse>(service.getBalance(), HttpStatus.OK);
    }
    @PostMapping(value="/pay")
    public ResponseEntity<?> payRequest(@RequestBody PayRequest request) {
        return service.requestToPay(request);
    }
    @GetMapping(value = "/status/{referenceId}")
    public ResponseEntity<?> getRequestStatus(@PathVariable String referenceId) {
        return service.requestStatus(referenceId);
    }
}
