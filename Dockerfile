# Multi-stage build
FROM eclipse-temurin:21-jdk-jammy AS builder

# Çalışma dizini
WORKDIR /app

# Source dosyalarını kopyala
COPY src/ src/
COPY patients.txt patients.txt

# Java dosyalarını compile et
RUN javac -d out -cp src src/*.java

# JAR dosyası oluştur
RUN jar -cvfe PatientManagementSystem.jar Main -C out .

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Compiled JAR'ı kopyala
COPY --from=builder /app/PatientManagementSystem.jar .
COPY --from=builder /app/patients.txt .

# Programı çalıştır
CMD ["java", "-jar", "PatientManagementSystem.jar"]