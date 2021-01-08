package rw.momo.api.momoapi.model;

public class payer {
    private String partyIdType = "MSISDN";
    private String partyId = "46733123453";

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
}
