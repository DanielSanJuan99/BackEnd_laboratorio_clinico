FROM eclipse-temurin:21-jdk-alpine

# Creamos un directorio para la app
WORKDIR /app

# Copiamos el archivo JAR generado (asegúrate de hacer 'mvn clean package' antes)
# El nombre 'app.jar' es un alias que usaremos dentro del contenedor
COPY target/*.jar app.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Definimos la variable de entorno para la Wallet (importante para Oracle)
ENV TNS_ADMIN=/app/wallet

# Comando para iniciar la app
ENTRYPOINT ["java", "-jar", "app.jar"]