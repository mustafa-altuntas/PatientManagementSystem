import java.io.*;

import java.util.HashMap;
import java.util.Map;

public class FileDataAccess implements  IDataAccess{

    private final String fileName;

    public FileDataAccess(String fileName) {
        this.fileName = fileName;
    }

    public FileDataAccess() {
        this("patients.txt");
    }


    @Override
    public Map<String, Patient> loadPatients() throws DataAccessException {
        Map<String, Patient> patients = new HashMap<>();

        if (!dataFileExists()) {
            return patients;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            int successCount = 0;
            int errorCount = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue; // Boş satırları atla
                }

                Patient patient = Patient.fromFileFormat(line);
                if (patient != null) {
                    patients.put(patient.getId(), patient);
                    successCount++;
                } else {
                    System.err.println("Hatalı veri formatı - Satır " + lineNumber + ": " + line);
                    errorCount++;
                }
            }

            System.out.println("Dosyadan veri yükleme tamamlandı:");
            System.out.println("- Başarılı: " + successCount + " kayıt");
            if (errorCount > 0) {
                System.out.println("- Hatalı: " + errorCount + " kayıt");
            }

        } catch (FileNotFoundException e) {
            throw new DataAccessException("Veri dosyası bulunamadı: " + fileName, e);
        } catch (IOException e) {
            throw new DataAccessException("Dosya okuma hatası: " + fileName, e);
        }

        return patients;
    }

    @Override
    public void savePatients(Map<String, Patient> patients) throws DataAccessException {
        if (patients == null) {
            throw new DataAccessException("Kaydedilecek hasta verisi null olamaz");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Patient patient : patients.values()) {
                writer.println(patient.toFileFormat());
            }
            writer.flush();

        } catch (IOException e) {
            throw new DataAccessException("Dosya yazma hatası: " + fileName, e);
        }
    }

    @Override
    public boolean dataFileExists() {
        File file = new File(fileName);
        return file.exists() && file.isFile();
    }



}
