package rw.momo.api.momoapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.momo.api.momoapi.model.PayResponse;
import rw.momo.api.momoapi.sevice.IResponseService;

@RestController
@RequestMapping("/momo/callback")
public class Callback {
    Logger LOGGER = LoggerFactory.getLogger(Callback.class);
    @Autowired
    IResponseService service;
    @PutMapping(value = "pay-request")
    public void requestCallback(@RequestBody PayResponse response) {
        LOGGER.info("callback: {}", response);
        service.setStusRequest(response.getReferenceId(), "successful");
    }
}
