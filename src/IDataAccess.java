import java.util.Map;

public interface IDataAccess {

    Map<String, Patient> loadPatients() throws DataAccessException;


    void savePatients(Map<String, Patient> patients) throws DataAccessException;

    boolean dataFileExists();


}
