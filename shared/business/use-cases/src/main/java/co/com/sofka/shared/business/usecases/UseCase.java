package co.com.sofka.shared.business.usecases;

public interface UseCase<RequestType, ResponseType> {
    public ResponseType execute(RequestType request);
}
