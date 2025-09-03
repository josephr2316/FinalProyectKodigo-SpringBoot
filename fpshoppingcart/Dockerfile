# Multi-stage build para optimizar el tamaño de la imagen
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de dependencias primero (para cache de Maven)
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .

# Descargar dependencias
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src/ src/

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Segunda etapa: Runtime
FROM eclipse-temurin:21-jre-alpine

# Instalar dependencias del sistema
RUN apk add --no-cache tzdata

# Establecer zona horaria
ENV TZ=UTC

# Crear usuario no-root
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Establecer directorio de trabajo
WORKDIR /app

# Copiar JAR desde la etapa de build
COPY --from=build /app/target/fpshoppingcart-0.0.1-SNAPSHOT.jar app.jar

# Cambiar propietario del archivo
RUN chown appuser:appgroup app.jar

# Cambiar al usuario no-root
USER appuser

# Exponer puerto
EXPOSE 8080

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=docker
ENV SERVER_PORT=8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
