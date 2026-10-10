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

Инструкция, как запустить проект. Виртуальная машина (**94.228.123.24**) используется
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
ssh student@94.228.123.24
```
Перейдите в папку с проектом и запустите контейнеры:
```bash
cd ~/diploma-materials
docker-compose up -d
Проверить, что контейнеры запущены:
```

docker ps
Ожидаемый результат:
```bash
text
CONTAINER ID   IMAGE              PORTS                    NAMES
xxxxxxxxxxxx   mysql:8.0          0.0.0.0:3306->3306/tcp   mysql-diploma
xxxxxxxxxxxx   postgres:15        0.0.0.0:5432->5432/tcp   postgres-diploma
xxxxxxxxxxxx   node:18-alpine     0.0.0.0:9999->9999/tcp   gate-simulator
```
2. Скопировать материалы диплома на локальную машину
На локальном компьютере скопируйте файлы с сервера:
```bash
scp -r student@94.228.123.24:~/diploma-materials ./
cd diploma-materials
```
3. Запустить SUT на локальной машине
Настройки подключения к БД и адреса gate-simulator берутся из файла
application.properties, который лежит в корне проекта:

properties
spring.credit-gate.url=http://94.228.123.24:9999/credit
spring.payment-gate.url=http://94.228.123.24:9999/payment
spring.datasource.url=jdbc:mysql://94.228.123.24:3306/app
spring.datasource.username=app
spring.datasource.password=App!Secur3#2026
Запустить SUT (с MySQL, настройки из application.properties):

```bash
java -jar artifacts/aqa-shop.jar
Альтернатива — передать параметры через командную строку:
```
```bash
java "-Dspring.datasource.url=jdbc:mysql://94.228.123.24:3306/app" "-Dspring.datasource.username=app" "-Dspring.datasource.password=App!Secur3#2026" -jar artifacts/aqa-shop.jar
```
С PostgreSQL:

```bash
java "-Dspring.datasource.url=jdbc:postgresql://94.228.123.24:5432/app" "-Dspring.datasource.username=app" "-Dspring.datasource.password=App!Secur3#2026" -jar artifacts/aqa-shop.jar
```
SUT запустится на порту 8080. Проверить:

```bash
curl http://localhost:8080
```
4. Проверить доступность SUT
Откройте в браузере:
text
http://localhost:8080
Должна открыться страница «Путешествие дня».

5. Запустить автотесты на локальной машине
С MySQL:

```bash
./gradlew clean test "-Ddb.url=jdbc:mysql://94.228.123.24:3306/app" "-Ddb.password=App!Secur3#2026"
```
С PostgreSQL:

```bash
./gradlew clean test "-Ddb.url=jdbc:postgresql://94.228.123.24:5432/app" "-Ddb.password=App!Secur3#2026"
```
6. Посмотреть отчёт Gradle
```bash
start build/reports/tests/test/index.html
```
Настройка build.gradle
В файле build.gradle в секции test добавьте строки:
gradle
test {
    useJUnitPlatform()
    systemProperty 'db.url', System.getProperty('db.url')
    systemProperty 'db.password', System.getProperty('db.password')
}
В классе DbUtils, работающем с БД, значения параметров получаются так:
java
private static final String DB_URL = System.getProperty("db.url");
private static final String DB_USER = "app";
private static final String DB_PASSWORD = System.getProperty("db.password", "App!Secur3#2026");
Остановка сервисов
После завершения работы остановите контейнеры на виртуальной машине:

```bash
docker-compose down
```
Остановите SUT на локальной машине:

```bash
pkill -f aqa-shop.jar
```
(на Windows — нажать Ctrl+C в окне с aqa-shop.jar)

Лицензия
Проект создан в учебных целях в рамках дипломной работы по профессии
«Тестировщик» (Нетология). Коммерческое использование не предполагается.
