import java.util.Map;

public interface IPatientService {


    boolean addPatient(Patient patient) throws PatientServiceException;

    boolean updatePatient(Patient patient) throws PatientServiceException;


    boolean deletePatient(String id) throws PatientServiceException;

    Patient getPatient(String id) throws PatientServiceException;


    Map<String, Patient> getAllPatients() throws PatientServiceException;

}
