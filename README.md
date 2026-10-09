# Дипломный проект: автоматизация тестирования комплексного сервиса «Путешествие дня»

## Описание проекта

Проект представляет собой автоматизацию тестирования веб-сервиса **«Путешествие дня»**, 
который предлагает купить тур двумя способами:

- **Обычная оплата** по дебетовой карте (Payment Gate);
- **Выдача кредита** по данным банковской карты (Credit Gate).

Приложение не обрабатывает данные карт самостоятельно, а пересылает их банковским 
сервисам-эмуляторам. В собственной СУБД приложение сохраняет информацию о том, 
успешно ли был совершён платёж и каким способом.

### Проектная документация

- [План автоматизации](docs/Plan.md)
- [Отчёт о тестировании](docs/Report.md)
- [Отчёт об автоматизации](docs/Summary.md)

---

## Начало работы

Инструкция, как запустить проект. Виртуальная машина (185.119.56.254) используется 
**только для запуска контейнеров** (СУБД и эмулятор банка). Учебное приложение и 
тесты запускаются **на локальной машине**.

### Prerequisites

**На виртуальной машине (сервере):**

- **Docker** и **Docker Compose** — для запуска СУБД и эмулятора банка.

**На локальной машине:**

- **Git** — для клонирования репозитория;
- **Java 11+** (JDK) — для запуска SUT и автотестов;
- **Браузер** (Chrome или Firefox) — для UI-тестов.

---

## Установка и запуск

### 1. Запустить контейнеры на виртуальной машине

Подключитесь к серверу:

```bash
ssh student@185.119.56.254
```

Перейдите в папку с проектом и запустите контейнеры:

```bash
cd ~/diploma-materials
docker-compose up -d
```

Проверить, что контейнеры запущены:

```bash
docker ps
```

Ожидаемый результат:

```
CONTAINER ID   IMAGE              PORTS                    NAMES
xxxxxxxxxxxx   mysql:8.0          0.0.0.0:3306->3306/tcp   mysql-diploma
xxxxxxxxxxxx   postgres:15        0.0.0.0:5432->5432/tcp   postgres-diploma
xxxxxxxxxxxx   node:18-alpine     0.0.0.0:9999->9999/tcp   gate-simulator
```

### 2. Скопировать материалы диплома на локальную машину

На **локальном компьютере** скопируйте файлы с сервера:

```bash
scp -r student@185.119.56.254:~/diploma-materials ./
cd diploma-materials
```

### 3. Запустить SUT на локальной машине

**С MySQL:**

```bash
java "-Dspring.datasource.url=jdbc:mysql://185.119.56.254:3306/app" -jar artifacts/aqa-shop.jar
```

**С PostgreSQL:**

```bash
java "-Dspring.datasource.url=jdbc:postgresql://185.119.56.254:5432/app" -jar artifacts/aqa-shop.jar
```

SUT запустится на порту **8080**. Проверить:

```bash
curl http://localhost:8080
```

### 4. Проверить доступность SUT

Откройте в браузере:

```
http://localhost:8080
```

Должна открыться страница «Путешествие дня».

### 5. Запустить автотесты на локальной машине

**С MySQL:**

```bash
./gradlew clean test "-Ddb.url=jdbc:mysql://185.119.56.254:3306/app"
```

**С PostgreSQL:**

```bash
./gradlew clean test "-Ddb.url=jdbc:postgresql://185.119.56.254:5432/app"
```

### 6. Посмотреть отчёт Allure

```bash
./gradlew allureServe
```

---

## Настройка `build.gradle`

В файле `build.gradle` в секции `test` добавьте строку:

```gradle
test {
    useJUnitPlatform()
    systemProperty 'db.url', System.getProperty('db.url')
    systemProperty 'chromeoptions.prefs', System.getProperty('chromeoptions.prefs', "profile.password_manager_leak_detection=false")
}
```

В классе `DbUtils`, работающем с БД, значение параметра получается так:

```java
private static final String DB_URL = System.getProperty("db.url");
```

---

## Остановка сервисов

После завершения работы остановите контейнеры на виртуальной машине:

```bash
docker-compose down
```

Остановите SUT на локальной машине:

```bash
pkill -f aqa-shop.jar
```

---

## Лицензия

Проект создан в учебных целях в рамках дипломной работы по профессии 
«Тестировщик» (Нетология). Коммерческое использование не предполагается.
