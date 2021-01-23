package rw.momo.api.momoapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
        res.setAmount("100");
        res.setCurrency("RWF");
        when(repository.save(res)).thenReturn(res);
        PayResponse responseActual = service.createRequest(res);
        assertEquals(res, responseActual);

    }
    @Test
    public void setStusRequestTest() {
        doNothing().when(repository).updateStatus(Mockito.anyString(), Mockito.any(EStatus.class));
        // when(repository.updateStatus(Mockito.anyString(), Mockito.any(EStatus.class)));
        assertTrue(service.setStusRequest("","FAILED").get());
    }
    @Test
    public void setStusRequestTest_UNDEFINED_status() {
        assertThrows(RuntimeException.class, () -> {
            service.setStusRequest("referenceId","unkown_status");
        });
    }
    @Test
    public void setStusRequestTest_Argument_capture() {
        service.setStusRequest("referenceId", "FAILED");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<EStatus> captor2 = ArgumentCaptor.forClass(EStatus.class);
        verify(repository).updateStatus(captor.capture(), captor2.capture());
        assertEquals(captor.getValue(), "referenceId");
        assertEquals(captor2.getValue(), EStatus.failed);
    }
}
