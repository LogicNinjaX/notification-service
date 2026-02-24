# 🚀 Notification Service

A scalable, multi-channel **Notification Service** built with **Spring Boot**, supporting:

* 📧 Email
* 📱 SMS
* 💬 WhatsApp
* 🤖 Telegram
* 🔔 Push (extensible)

Designed with **Kafka-based fan-out**, retry mechanisms, JWT security, and template-driven messaging.

---

### Core Concepts

* **NotificationTemplate** → Defines message structure
* **NotificationRequest** → Represents a send operation
* **NotificationMessage** → One per recipient
* **NotificationDelivery** → Tracks send attempts & retries

---

## ✨ Features

* Multi-channel support (Email, SMS, WhatsApp, Telegram)
* Kafka-based asynchronous processing
* Template-based rendering (Thymeleaf)
* Retry mechanism with configurable max attempts
* JWT-based authentication
* Role-based security
* Email domain validation
* SMS & content length control
* Swagger/OpenAPI documentation
* HTML sanitization with Jsoup
* MapStruct for DTO mapping
* Scheduled retry processing

---

## 🛠️ Tech Stack

* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* Spring Kafka
* Thymeleaf
* MySQL
* Twilio SDK (SMS & WhatsApp)
* JWT (jjwt)
* OpenAPI (springdoc)
* MapStruct

---

## 📂 Project Structure (High-Level)

```
notification-service/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── nitish/
    │   │           └── notification_service
    │   │               ├── NotificationServiceApplication.java  # Main Spring Boot entry point
    │   │               ├── channel      # Channel implementations (Email, SMS, WhatsApp, Telegram)
    │   │               ├── config       # Configuration classes (Kafka, Security, Beans, etc.)
    │   │               ├── controller   # REST API controllers
    │   │               │   └── doc      # Swagger documentation configs
    │   │               ├── dto          # Data Transfer Objects
    │   │               │   ├── request  # Incoming API request DTOs
    │   │               │   └── response # API response DTOs
    │   │               ├── entity       # JPA entities (DB tables mapping)
    │   │               ├── enums        # Enum definitions (Status, ChannelType, etc.)
    │   │               ├── exception    # Custom exception handling
    │   │               │   ├── custom_exception   # Business & application-specific exceptions
    │   │               │   └── handler            # Global exception handlers (@ControllerAdvice)
    │   │               ├── messaging       # Kafka messaging layer
    │   │               │   ├── consumer    # Kafka consumers (message processing)
    │   │               │   └── producer    # Kafka producers (event publishing)
    │   │               ├── repository      # Spring Data JPA repositories
    │   │               ├── scheduler       # Scheduled jobs (retry failed notifications)
    │   │               ├── security        # JWT, filters, authentication & authorization logic
    │   │               ├── service         # Business logic layer
    │   │               └── util            # Utility/helper classes
    │   │                   └── mapper      # MapStruct mappers (Entity ↔ DTO conversion)
    │   └── resources/              
    │       ├── application.yaml      # Application configuration
    │       └── messages.properties   # Validation messages
    └── test  # Unit & integration tests
```
---

# ⚙️ Configuration

## 📌 application.yml

```yaml
spring:
  application:
    name: notification-service

  datasource:
    url: ${DB_URL}
    username: ${DB_UNAME}
    password: ${DB_PASS}
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
        highlight_sql: true
    show-sql: true

  kafka:
    bootstrap-servers:
      - ${KAFKA_SERVER_URL}
```

---

## 🔐 Security Configuration

JWT-based authentication:

```yaml
app:
  jwt:
    secret: ${JWT_SECRET}
    expiration: ${JWT_EXP}
```

---

## 📧 Email Configuration

```yaml
spring:
  mail:
    host: ${SMTP_HOST}
    port: ${SMTP_PORT}
    username: ${SMTP_UNAME}
    password: ${SMTP_PASS}
```

Allowed domains:

```yaml
app:
  notification:
    channel-type:
      email:
        allowed-domains:
          - gmail.com
          - yahoo.com
          - hotmail.com
          - outlook.com
```

---

## 📱 SMS & WhatsApp (Twilio)

```yaml
app:
  twilio:
    sid: ${ACCOUNT_SID}
    token: ${AUTH_TOKEN}

  notification:
    channel-type:
      sms:
        content-length: ${SMS_LENGTH}
        phone-number: ${PHONE_NUMBER}
      whatsapp:
        from-number: ${WA_NUMBER}
```

---

## 🤖 Telegram

```yaml
app:
  notification:
    channel-type:
      telegram:
        bot-token: ${TG_BOT_TOKEN}
```

---

## 🔁 Retry Configuration

```yaml
app:
  notification:
    max-attempts: ${MAX_ATTEMPTS}

  scheduler:
    failed-notification-delay: ${FAILED_NOTIFICATION_DELAY}
```

---

# 📬 How Sending Works

### 1️⃣ Client sends request

```json
{
  "templateId": "550e8400-e29b-41d4-a716-446655440000",
  "recipients": [
    "user1@gmail.com",
    "user2@gmail.com"
  ],
  "variables": {
    "name": "John"
  }
}
```

### 2️⃣ System creates:

* 1 `NotificationRequest`
* N `NotificationMessage`
* Kafka events published

### 3️⃣ Consumers process messages

* Send via channel
* Create `NotificationDelivery`
* Retry if needed

---

# 🔄 Retry Mechanism

* Each message tracks delivery attempts
* If failed:

    * Retry until `max-attempts`
    * Move to FAILED state after limit
* Scheduled job reprocesses failed messages

---

# 🔍 API Documentation

Swagger UI available at:

```
http://localhost:8080/swagger-ui.html
```

---

# 🔐 Authentication

All protected APIs require:

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 🧪 Running the Project

### 1️⃣ Start MySQL

Ensure database is running and environment variables are set.

### 2️⃣ Start Kafka

Ensure Kafka broker is accessible via:

```
${KAFKA_SERVER_URL}
```

### 3️⃣ Run Application

```bash
mvn clean install
mvn spring-boot:run
```

---

# 🧠 Design Highlights

* Fan-out per recipient for reliability
* Channel isolation using strategy pattern
* Database as source of truth
* Async processing with Kafka
* Full audit trail via delivery table
* Secure JWT-based authentication
* Config-driven channel limits

---

# 📈 Future Enhancements

* Dead Letter Queue (DLQ)
* Template versioning
* Multi-tenant support
* Rate limiting
* Metrics & observability (Prometheus/Grafana)
* Docker & Kubernetes deployment

---

## 👨‍💻 Author

**Nitish Kr Sahni**
Java Backend Developer | Spring Boot

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Nitish%20Sahni-blue?logo=linkedin)](https://www.linkedin.com/in/nitish-sahni/)
[![GitHub](https://img.shields.io/badge/GitHub-LogicNinjaX-black?logo=github)](https://github.com/LogicNinjaX)

---
