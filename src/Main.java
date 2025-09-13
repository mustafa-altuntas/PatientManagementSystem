public class Main {
    public static void main(String[] args) {
        try {
            IDataAccess dataAccess = new FileDataAccess("patients.txt");

            PatientManagementSystem system = new PatientManagementSystem(new PatientService(dataAccess));
            system.run();
        } catch (PatientServiceException e) {
            throw new RuntimeException(e);
        }

    }
}