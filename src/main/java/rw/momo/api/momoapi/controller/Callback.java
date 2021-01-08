package rw.momo.api.momoapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.momo.api.momoapi.model.PayRequest;

@RestController
@RequestMapping("/momo/callback")
public class Callback {
    Logger LOGGER = LoggerFactory.getLogger(Callback.class);
    @PostMapping(value = "pay-request")
    public void requestCallback(@RequestBody PayRequest request) {
        LOGGER.info("callback: {}", request);
    }
}
