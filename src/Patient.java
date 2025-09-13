public class Patient {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email adresi boş olamaz!");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Geçersiz email formatı! Örn: kullanici@domain.com");
        }

        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Telefon numarası boş olamaz!");
        }

        // Sadece rakam ve opsiyonel + işaretiyle başlayabilir (örnek: +905551112233 veya 05551112233)
        if (!phoneNumber.matches("^\\+?[0-9]{10,15}$")) {
            throw new IllegalArgumentException("Geçersiz telefon numarası formatı! Örn: +905551112233 veya 05551112233");
        }

        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Yaş 0 ile 150 arasında olmalıdır!");
        }
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Soyad boş olamaz!");
        }
        if (lastName.length() < 2) {
            throw new IllegalArgumentException("Soyad en az 2 karakter olmalıdır!");
        }
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("Ad boş olamaz!");
        }
        if (firstName.length() < 2) {
            throw new IllegalArgumentException("Ad en az 2 karakter olmalıdır!");
        }
        this.firstName = firstName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Patient(String id, String firstName, String lastName, int age, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public Patient(){

    }



    @Override
    public String toString() {
        return String.format("ID: %s | Ad: %s | Soyad: %s | Yaş: %d | Telefon: %s | Email: %s",
                id, firstName, lastName, age, phoneNumber, email);
    }


    public String toFileFormat() {
        return id + "," + firstName + "," + lastName + "," + age + "," + phoneNumber + "," + email;
    }


    public static Patient fromFileFormat(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(",");
        if (parts.length == 6) {
            try {
                return new Patient(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        Integer.parseInt(parts[3].trim()),
                        parts[4].trim(),
                        parts[5].trim()
                );
            } catch (NumberFormatException e) {
                System.err.println("Hatalı yaş formatı: " + line);
                return null;
            }
        }
        return null;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patient patient = (Patient) obj;
        return id.equals(patient.id);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
