# Отчёт о проведённом тестировании

## Краткое описание

Проведено автоматизированное тестирование комплексного сервиса покупки тура
(`aqa-shop.jar`). Тестирование охватывает:

- оплату тура по дебетовой карте (Payment Gate);
- выдачу кредита по данным банковской карты (Credit Gate);
- валидацию полей формы оплаты;
- корректность записи данных в СУБД MySQL.

**Тестируемая версия:** `aqa-shop.jar` (v0.0.1-SNAPSHOT)
**Дата тестирования:** 09.10.2026

## Окружение

| Параметр | Значение |
|----------|----------|
| ОС | Windows 10 |
| Браузер | Firefox |
| Java | 11 |
| Selenide | 5.2.1 |
| JUnit | 5 |
| Gradle | 8.6 |
| СУБД | MySQL 8.0.18 (Docker) |
| Gate-simulator | Node.js 8.16.2-alpine (Docker) |
| SUT | порт 8080, запущен на Windows |
| БД и gate-simulator | удалённый сервер, Docker |

## Результаты прогона тестов

| Показатель | Значение |
|-----------|----------|
| Всего тест-кейсов | 14 |
| Успешных | 2 (14%) |
| Неуспешных | 12 (86%) |
| Пропущено | 0 |
| Время выполнения | 3 мин 41 сек |
<img width="778" height="584" alt="image" src="https://github.com/user-attachments/assets/142930e8-8d20-4591-a702-3d01d1375d03" />

**Отчёт Gradle:** `build/reports/tests/test/index.html`

## Успешные сценарии

1.  Оплата тура APPROVED-картой `4444 4444 4444 4441`.
2.  Покупка в кредит APPROVED-картой `4444 4444 4444 4441`.

## Найденные баги (Issues)

Все обнаруженные баги оформлены в Issues репозитория
[`diplomaaa`](https://github.com/alinasadness-cpu/diplomaaa/issues):

| Issue | Название |
|-------|----------|
| [#1](https://github.com/alinasadness-cpu/diplomaaa/issues/1) | DECLINED-карта даёт «Успешно. Операция одобрена банком» |
| [#2](https://github.com/alinasadness-cpu/diplomaaa/issues/2) | Поле «Владелец» принимает цифры |
| [#3](https://github.com/alinasadness-cpu/diplomaaa/issues/3) | Поле «Владелец» принимает спецсимволы |
| [#4](https://github.com/alinasadness-cpu/diplomaaa/issues/4) | Поле «Владелец» принимает кириллицу |
| [#5](https://github.com/alinasadness-cpu/diplomaaa/issues/5) | Месяц «00» не отклоняется валидацией |

## Проблемы автотестов (не баги SUT)

| Тест | Причина падения |
|------|-----------------|
| `ValidationTest.shouldShowErrorForMonth13` | Неверный селектор: SUT показывает ошибку под полем `.input__sub`, а тест ищет уведомление `.notification_status_error`. |
| `ValidationTest.shouldShowErrorForExpiredYear` | То же самое. |

Эти два падения — недоработки самих тестов, требуют правки селектора.

## Общие рекомендации

1. **Критично:** исправить обработку DECLINED-карт.
2. **Критично:** добавить полную валидацию полей формы:
   - номер карты — ровно 16 цифр, только цифры;
   - CVC/CVV — ровно 3 цифры;
   - владелец — только латиница, буквы и пробел;
   - месяц — от 01 до 12;
   - год — не меньше текущего.
3. **Важно:** доработать UI-тесты для проверки ошибок под полями.
4. **Рекомендуется:** добавить логирование запросов к Gate и записей в БД.

## Артефакты

- Issues: https://github.com/alinasadness-cpu/diplomaaa/issues
- Отчёт Gradle: `build/reports/tests/test/index.html`
- Исходный код: https://github.com/alinasadness-cpu/diplomaaa
