# Используем базовый образ с JDK
FROM openjdk:17-jdk-slim

# Указываем рабочую директорию
WORKDIR /app

# Копируем файл JAR
COPY target/dispatcher.jar dispatcher.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "dispatcher.jar"]