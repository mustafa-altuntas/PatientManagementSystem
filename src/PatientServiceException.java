public class PatientServiceException  extends Exception {

    public PatientServiceException(String message) {
        super(message);
    }

    public PatientServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PatientServiceException(Throwable cause) {
        super(cause);
    }


}
