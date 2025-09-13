import java.util.HashMap;
import java.util.Map;

public class PatientService implements IPatientService {

    private Map<String, Patient> patients;
    private IDataAccess dataAccess;

    public PatientService(IDataAccess dataAccess) throws PatientServiceException {
        if (dataAccess == null) {
            throw new PatientServiceException("DataAccess null olamaz");
        }

        this.dataAccess = dataAccess;
        this.patients = new HashMap<>();

        loadPatientsFromDataSource();
    }


    @Override
    public boolean addPatient(Patient patient) throws PatientServiceException {

        validatePatientInput(patient);
        patients.put(patient.getId(), patient);
        savePatientsToDataSource();

        return true;


    }

    @Override
    public boolean updatePatient(Patient patient) throws PatientServiceException {
        validatePatientInput(patient);

        Patient currentPatient = patients.get(patient.getId());
        if (currentPatient == null) {
            return false;
        }

        currentPatient.setFirstName(patient.getFirstName());
        currentPatient.setLastName(patient.getLastName());
        currentPatient.setAge(patient.getAge());
        currentPatient.setPhoneNumber(patient.getPhoneNumber());
        currentPatient.setEmail(patient.getEmail());

        savePatientsToDataSource();

        return true;
    }

    @Override
    public boolean deletePatient(String id) throws PatientServiceException {
        if (id == null || id.trim().isEmpty()) {
            throw new PatientServiceException("Hasta ID'si boş olamaz");
        }

        Patient removedPatient = patients.remove(id);
        if (removedPatient != null) {
            savePatientsToDataSource();
            return true;
        }
        return false;
    }

    @Override
    public Patient getPatient(String id) throws PatientServiceException {
        if (id == null || id.trim().isEmpty()) {
            throw new PatientServiceException("Hasta ID'si boş olamaz");
        }

        return patients.get(id);
    }

    @Override
    public Map<String, Patient> getAllPatients() throws PatientServiceException {
        return new HashMap<>(patients);
    }


    private void loadPatientsFromDataSource() throws PatientServiceException {
        try {
            patients = dataAccess.loadPatients();
        } catch (DataAccessException e) {
            throw new PatientServiceException("Hasta verileri yüklenirken hata oluştu", e);
        }
    }

    private void savePatientsToDataSource() throws PatientServiceException {
        try {
            dataAccess.savePatients(patients);
        } catch (DataAccessException e) {
            throw new PatientServiceException("Hasta verileri kaydedilirken hata oluştu", e);
        }
    }


    private void validatePatientInput(Patient patient) throws PatientServiceException {

        if (patient.getId() == null || patient.getId().trim().isEmpty()) {
            throw new PatientServiceException("Hasta ID'si boş olamaz");
        }

        if (patient.getFirstName() == null || patient.getFirstName().trim().isEmpty()) {
            throw new PatientServiceException("Hasta adı boş olamaz");
        }

        if (patient.getLastName() == null || patient.getLastName().trim().isEmpty()) {
            throw new PatientServiceException("Hasta soyadı boş olamaz");
        }

        if (patient.getAge() < 0 || patient.getAge() > 150) {
            throw new PatientServiceException("Hasta yaşı 0-150 arasında olmalıdır");
        }

        if (patient.getPhoneNumber() == null || patient.getPhoneNumber().trim().isEmpty()  || !patient.getPhoneNumber().matches("^\\+?[0-9]{10,15}$")) {
            throw new PatientServiceException("Geçersiz telefon numarası");
        }

        if (patient.getEmail() == null || patient.getEmail().trim().isEmpty()) {
            throw new PatientServiceException("Hasta email'i boş olamaz");
        }

        if (!patient.getEmail().contains("@") || !patient.getEmail().contains(".")) {
            throw new PatientServiceException("Geçersiz email formatı");
        }
    }
}
