package tech.maslov.sandbox.client.api.request;

public class ClientLoginApiRequest {
    private Long phoneNumber;
    private Integer code;

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
