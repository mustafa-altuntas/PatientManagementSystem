# Patient Management System

Bu proje, Java programlama dili ile Nesne Yönelimli Programlama (OOP) prensipleri kullanılarak geliştirilmiş bir hasta kayıt sistemidir. Sistem, kullanıcıların hastaları ID ile kaydetmelerini, listelemelerini, güncellemelerini ve silmelerini sağlar. Hastaların bilgileri HashMap veri yapısı ile saklanır. Veritabanı olarak ise text dosyası kullanılır ve tüm hasta bilgileri dosyaya kaydedilir.

##  Proje Özellikleri

### 📝 Hasta Kaydetme
- Kullanıcı, hasta bilgilerini (ad soyad, yaş, telefon, email) girerek yeni hasta kaydeder
- Her hasta, benzersiz bir ID ile tanımlanır. ID'ler eşsiz olmalıdır

### 📋 Hasta Listeleme
- Sistemde kayıtlı olan tüm hastaların bilgileri liste olarak gösterilir
- Hasta ID'si, adı, soyadı, yaşı, telefonu ve e-posta bilgileri görüntülenir

### ✏️ Hasta Güncelleme
- Kullanıcı, mevcut hastaların bilgilerini ID'ye göre güncelleyebilir
- Ad, soyad, yaş, telefon ve e-posta gibi bilgiler değiştirilebilir

### 🗑️ Hasta Silme
- Kullanıcı, bir hastayı ID ile silme işlemi gerçekleştirebilir
- Silme işlemi başarılı olduğunda, hastanın bilgileri HashMap'ten ve dosyadan kaldırılır

### 💾 Veri Kaydetme ve Yükleme
- Tüm hasta bilgileri bir text dosyasına kaydedilir
- Sistem çalıştığında, önceki kaydedilmiş hasta verileri dosyadan yüklenir

## 🛠️ Teknolojiler

- **Java 21** - Ana programlama dili
- **HashMap** - Veri saklama yapısı
- **File I/O** - Veri kalıcılığı için dosya işlemleri
- **Docker** - Konteyner teknolojisi
- **Docker Compose** - Servis yönetimi

## 📁 Proje Yapısı

```
PatientManagementSystem/
├── src/
│   ├── Main.java
│   ├── Patient.java
│   ├── PatientService.java
│   ├── IPatientService.java
│   ├── PatientManagementSystem.java
│   ├── FileDataAccess.java
│   ├── IDataAccess.java
│   ├── DataAccessException.java
│   └── PatientServiceException.java
├── patients.txt
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## 🐳 Docker ile Çalıştırma

### Gereksinimler
- Docker
- Docker Compose

## Kurulum ve Çalıştırma

### Docker ile Çalıştırma (Önerilen)
1. **Projeyi klonlayın:**
    ```bash
    git clone https://github.com/mustafa-altuntas/PatientManagementSystem.git
    ```
    ```bash
    cd PatientManagementSystem
    ```

2. **Docker Compose ile başlatın:**
    ```bash
    docker compose up -d
    ```

3. **Interaktif modda çalıştırın:**

    ```bash
    docker compose exec patient-app java -jar PatientManagementSystem.jar
    ```
    veya
    ```bash
    docker attach patient-management
    ```

4. **Console uygulamasını görene kadar Enter tuşuna birkaç kez basın**


### Yerel Çalıştırma

1. **Projeyi klonlayın:**
    ```bash
    git clone https://github.com/mustafa-altuntas/PatientManagementSystem.git
    ```
    ```bash
    cd PatientManagementSystem
    ```

2. **Projeyi derleyin:**
   ```bash
   javac -d out src/*.java
   ```

3. **Uygulamayı Çalıştır ve Komut Satırı Argümanını Gönder:**
   ```bash
   java -cp out Main
   ```

## 🖥️ Karşılama Ekranı

Sistem çalıştırıldığında aşağıdaki menü görüntülenir:

```
=========================================
           HASTA KAYIT SİSTEMİ
=========================================
1. Hasta Kaydet
2. Hastaları Listele
3. Hasta Güncelle
4. Hasta Sil
5. Hasta Ara
0. Çıkış
=========================================
Seçiminiz (0-5):
```
