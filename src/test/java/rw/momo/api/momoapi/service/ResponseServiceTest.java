package rw.momo.api.momoapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import rw.momo.api.momoapi.model.EStatus;
import rw.momo.api.momoapi.model.PayResponse;
import rw.momo.api.momoapi.model.TokenResponse;
import rw.momo.api.momoapi.repository.ResponseRepository;
import rw.momo.api.momoapi.sevice.MomoServiceImpl;
import rw.momo.api.momoapi.sevice.ResponseService;

@ExtendWith(MockitoExtension.class)
public class ResponseServiceTest {
    @InjectMocks
    ResponseService service;
    @Mock
    ResponseRepository repository;

    @Test
    public void createRequestTest() {
        PayResponse res = new PayResponse();
        when(repository.save(res)).thenReturn(res);
        PayResponse responseActual = service.createRequest(res);
        assertEquals(res, responseActual);

    }

}
