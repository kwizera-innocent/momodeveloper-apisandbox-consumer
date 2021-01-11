package rw.momo.api.momoapi.model;

import javax.persistence.Embeddable;

@Embeddable
public class Payer {
    private String partyIdType;
    private String partyId;

    public String getPartyIdType() {
        return partyIdType;
    }

    public void setPartyIdType(String partyIdType) {
        this.partyIdType = partyIdType;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public Payer(String partyIdType, String partyId) {
        this.partyIdType = partyIdType;
        this.partyId = partyId;
    }

    public Payer() {
    }
}
