# Usar la imagen oficial de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR generado
COPY build/libs/SIAC-0.0.1-SNAPSHOT.jar app.jar

COPY .env /app/.env

# Copiar el archivo de configuración application.properties al contenedor
COPY src/main/resources/application.properties /app/application.properties

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "/app/mi-aplicacion.jar"]
