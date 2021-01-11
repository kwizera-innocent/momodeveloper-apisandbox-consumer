package rw.momo.api.momoapi.sevice;

import java.util.Optional;

import rw.momo.api.momoapi.model.PayResponse;

public interface IResponseService {
    PayResponse createRequest(PayResponse response);
    Optional<Boolean> setStusRequest(String referenceId, String status);
}
