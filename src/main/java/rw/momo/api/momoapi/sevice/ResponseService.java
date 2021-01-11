package rw.momo.api.momoapi.sevice;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.momo.api.momoapi.model.EStatus;
import rw.momo.api.momoapi.model.PayResponse;
import rw.momo.api.momoapi.repository.ResponseRepository;

@Service
public class ResponseService implements IResponseService {
    ResponseRepository repository;
    Logger LOGGER = LoggerFactory.getLogger(ResponseService.class);
    

    @Override
    public PayResponse createRequest(PayResponse res) {
        res.setStatus(EStatus.ongoing);
        PayResponse response = repository.save(res);
        return response;
    }

    @Override
    public Optional<Boolean> setStusRequest(String referenceId, String status) {
        try {
            repository.updateStatus(referenceId, EStatus.valueOf(status.toLowerCase()));
            return Optional.of(true);
        } catch (Exception e) {
            LOGGER.debug(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
    @Autowired
    public ResponseService(ResponseRepository repository) {
        this.repository = repository;
    }
    
}
