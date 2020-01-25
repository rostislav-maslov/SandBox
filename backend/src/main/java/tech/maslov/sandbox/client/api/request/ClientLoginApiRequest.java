package tech.maslov.sandbox.client.api.request;

public class ClientLoginApiRequest {
    private Long phoneNumber;
    private Integer code;

    public ClientLoginApiRequest() {
    }

    public ClientLoginApiRequest(Long phoneNumber, Integer code) {
        this.phoneNumber = phoneNumber;
        this.code = code;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
