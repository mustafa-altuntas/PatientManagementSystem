import java.util.*;

public class PatientManagementSystem {


    private IPatientService patientService;
    private Scanner scanner;

    public PatientManagementSystem(IPatientService patientService) {
        this.patientService = patientService;
        try {

            this.scanner = new Scanner(System.in);

        } catch (Exception e) {
            System.err.println("Sistem başlatılırken hata oluştu: " + e.getMessage());
            System.exit(1);
        }
    }

    public void showMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("       HASTA KAYIT SİSTEMİ");
        System.out.println("=".repeat(40));
        System.out.println("1. Hasta Kaydet");
        System.out.println("2. Hastaları Listele");
        System.out.println("3. Hasta Güncelle");
        System.out.println("4. Hasta Sil");
        System.out.println("5. Hasta Ara");
        System.out.println("0. Çıkış");
        System.out.println("=".repeat(40));
        System.out.print("Seçiminiz (0-5): ");
    }


    private void addPatient() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("      Yeni Hasta Kaydı");
        System.out.println("-".repeat(30));

        try {

            var patient = new Patient();
            patient.setId(UUID.randomUUID().toString());

            // Ad
            while (true) {
                try {
                    System.out.print("Ad: ");
                    patient.setFirstName(scanner.nextLine().trim());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
            }

            // Soyad
            while (true) {
                try {
                    System.out.print("Soyad: ");
                    patient.setLastName(scanner.nextLine().trim());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
            }

            // Yaş
            while (true) {
                try {
                    System.out.print("Yaş: ");
                    int age = Integer.parseInt(scanner.nextLine().trim());
                    patient.setAge(age);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Geçersiz yaş formatı! Lütfen sayısal bir değer girin.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
            }

            // Telefon
            while (true) {
                try {
                    System.out.print("Telefon: ");
                    patient.setPhoneNumber(scanner.nextLine().trim());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
            }

            // Email
            while (true) {
                try {
                    System.out.print("Email: ");
                    patient.setEmail(scanner.nextLine().trim());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
            }


            if (patientService.addPatient(patient)) {
                System.out.println("Hasta başarıyla kaydedildi!");
            } else {
                System.out.println("Hasta kaydedilemedi!");
            }

        } catch (PatientServiceException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }


    private void listPatients() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("       Hasta Listesi");
        System.out.println("-".repeat(30));

        try {
            Map<String, Patient> allPatients = patientService.getAllPatients();

            if (allPatients.isEmpty()) {
                System.out.println("Sistemde kayıtlı hasta bulunmamaktadır.");
                return;
            }

            System.out.println("Toplam " + allPatients.size() + " hasta kayıtlı.\n");

            // ID'ye göre sıralı liste
            List<String> sortedIds = new ArrayList<>(allPatients.keySet());
            Collections.sort(sortedIds);

            int counter = 1;
            for (String id : sortedIds) {
                System.out.println(counter + ". " + allPatients.get(id));
                counter++;
            }

        } catch (PatientServiceException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }


    private void updatePatient() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("      Hasta Güncelleme");
        System.out.println("-".repeat(30));

        try {
            System.out.print("Güncellenecek Hasta ID: ");
            String id = scanner.nextLine().trim();

            Patient existingPatient = patientService.getPatient(id);
            if (existingPatient == null) {
                System.out.println("Bu ID ile hasta bulunamadı!");
                return;
            }

            System.out.println("\nMevcut bilgiler:");
            System.out.println(existingPatient);
            System.out.println("\nYeni bilgileri girin (boş bırakırsanız mevcut değer korunur):");

            // Ad
            String firstName;
            while (true) {
                System.out.print("Yeni Ad [" + existingPatient.getFirstName() + "]: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    firstName = existingPatient.getFirstName();
                    break;
                }
                try {
                    new Patient().setFirstName(input); // sadece validasyon için
                    firstName = input;
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
            }

            // Soyad
            String lastName;
            while (true) {
                System.out.print("Yeni Soyad [" + existingPatient.getLastName() + "]: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    lastName = existingPatient.getLastName();
                    break;
                }
                try {
                    new Patient().setLastName(input);
                    lastName = input;
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
            }

            // Yaş
            int age = existingPatient.getAge();
            while (true) {
                System.out.print("Yeni Yaş [" + age + "]: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) break;
                try {
                    int newAge = Integer.parseInt(input);
                    new Patient().setAge(newAge); // validasyon
                    age = newAge;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Geçersiz yaş formatı! Lütfen sayısal değer girin.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
            }

            // Telefon
            String phone;
            while (true) {
                System.out.print("Yeni Telefon [" + existingPatient.getPhoneNumber() + "]: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    phone = existingPatient.getPhoneNumber();
                    break;
                }
                try {
                    new Patient().setPhoneNumber(input);
                    phone = input;
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
            }

            // Email
            String email;
            while (true) {
                System.out.print("Yeni Email [" + existingPatient.getEmail() + "]: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    email = existingPatient.getEmail();
                    break;
                }
                try {
                    new Patient().setEmail(input);
                    email = input;
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Hata: " + e.getMessage());
                }
            }

            // Güncelleme
            if (patientService.updatePatient(new Patient(id, firstName, lastName, age, phone, email))) {
                System.out.println("Hasta bilgileri başarıyla güncellendi!");
            } else {
                System.out.println("Güncelleme işlemi başarısız!");
            }

        } catch (PatientServiceException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }



    private void deletePatient() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("        Hasta Silme");
        System.out.println("-".repeat(30));

        try {
            System.out.print("Silinecek Hasta ID: ");
            String id = scanner.nextLine().trim();

            Patient patient = patientService.getPatient(id);
            if (patient == null) {
                System.out.println("Bu ID ile hasta bulunamadı!");
                return;
            }

            System.out.println("\nSilinecek hasta:");
            System.out.println(patient);

            System.out.print("\nBu hastayı silmek istediğinizden emin misiniz? (E/H): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("e") || confirmation.equals("evet")) {
                if (patientService.deletePatient(id)) {
                    System.out.println("Hasta başarıyla silindi!");
                } else {
                    System.out.println("Silme işlemi başarısız!");
                }
            } else {
                System.out.println("Silme işlemi iptal edildi.");
            }

        } catch (PatientServiceException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }


    private void searchPatient() {
        System.out.println("\n" + "-".repeat(30));
        System.out.println("        Hasta Arama");
        System.out.println("-".repeat(30));

        try {
            System.out.print("Aranacak Hasta ID: ");
            String id = scanner.nextLine().trim();

            Patient patient = patientService.getPatient(id);
            if (patient != null) {
                System.out.println("\nHasta bulundu:");
                System.out.println(patient);
            } else {
                System.out.println("Bu ID ile hasta bulunamadı!");
            }

        } catch (PatientServiceException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }



    public void run() {
        while (true) {
            showMainMenu();

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        addPatient();
                        break;
                    case 2:
                        listPatients();
                        break;
                    case 3:
                        updatePatient();
                        break;
                    case 4:
                        deletePatient();
                        break;
                    case 5:
                        searchPatient();
                        break;
                    case 0:
                        System.out.println("\nSistem kapatılıyor... İyi günler!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Geçersiz seçim! Lütfen 0-8 arasında bir değer girin.");
                }

                System.out.print("\n>>> Devam etmek için Enter tuşuna basın...");
                scanner.nextLine();

            } catch (NumberFormatException e) {
                System.out.println("Geçersiz girdi! Lütfen sayısal bir değer girin.");
                System.out.print(">>> Devam etmek için Enter tuşuna basın...");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Beklenmeyen hata: " + e.getMessage());
                System.out.print(">>> Devam etmek için Enter tuşuna basın...");
                scanner.nextLine();
            }
        }
    }













}
