# Todo List REST API

Простое RESTful API для управления списком задач, разработанное на Spring Boot с использованием Java 17.

## Технологический стек

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (in-memory)
- MapStruct
- Lombok
- Maven

## Функциональность

- ✅ Создание новой задачи
- ✅ Получение всех задач
- ✅ Получение задачи по ID
- ✅ Обновление задачи
- ✅ Удаление задачи по ID
- ✅ Валидация данных
- ✅ Обработка ошибок
- ✅ Логирование

## Сборка и запуск

### Требования

- Java 17
- Maven 3.6+

### Сборка приложения

```bash
mvn clean compile
```

## Документация API (Swagger)

После запуска приложения документация API доступна по адресам:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Основные разделы документации

1. **Tasks** - все endpoints для управления задачами
2. **Schemas** - модели данных (TaskRequest, TaskResponse, ErrorResponse)

### Приложение будет доступно по адресу: http://localhost:8080

### База данных H2
Конфигурация H2
Режим: In-memory (данные сохраняются только во время работы приложения)
JDBC URL: jdbc:h2:mem:tododb
Пользователь: sa
Пароль: (пустой)

## Доступ к H2 Console
После запуска приложения вы можете получить доступ к консоли H2:
URL: http://localhost:8080/h2-console

### Настройки подключения:
JDBC URL: jdbc:h2:mem:tododb
User Name: sa
Password: (оставьте пустым)
