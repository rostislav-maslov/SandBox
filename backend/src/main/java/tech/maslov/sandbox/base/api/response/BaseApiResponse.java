package tech.maslov.sandbox.base.api.response;

public class BaseApiResponse<T> {

    private BaseErrorResponse error = new BaseErrorResponse();
    private T result;

    public BaseApiResponse() {
    }

    public BaseApiResponse(T result) {
        this.result = result;
    }

    public static<T> BaseApiResponse of(T result){
        return new BaseApiResponse(result);
    }

    public BaseErrorResponse getError() {
        return error;
    }

    public void setError(BaseErrorResponse error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

