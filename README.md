# Инструкция по запуску проекта

## Шаги для запуска

1. Запустите базу данных через Docker Compose:
   ```bash
   docker-compose up -d
   ```

2. Запустите приложение с помощью Gradle:
   ```bash
   ./gradlew bootRun
   ```

---

Для остановки базы данных выполните:
```bash
docker-compose down
```
# Описание эндпоинтов

## **Ссылки** (`/api/link`)

1. **Создание короткой ссылки**
   - **Метод**: `POST`
   - **URL**: `/api/link/shortingLink`
   - **Описание**: Создает короткую ссылку для указанного URL.
   - **Тело запроса**: 
     ```json
     {
       "originalUrl": "string",
       "userId": "UUID",
       "maxClicks": "integer",
       "durationDay": "integer"
     }
     ```
   - **Ответ**: Созданная короткая ссылка.

2. **Получение всех ссылок пользователя**
   - **Метод**: `GET`
   - **URL**: `/api/link/user/{userId}`
   - **Описание**: Возвращает список всех ссылок, принадлежащих указанному пользователю.
   - **Параметры**:
     - `userId` — UUID пользователя.
   - **Ответ**: Список ссылок.

3. **Обновление параметров ссылки**
   - **Метод**: `PUT`
   - **URL**: `/api/link/update`
   - **Описание**: Изменяет параметры ссылки (например, лимит кликов или срок действия).
   - **Тело запроса**: 
     ```json
     {
       "shortLink": "string",
       "userId": "UUID",
       "maxClicks": "integer",
       "durationDay": "integer"
     }
     ```
   - **Ответ**: Уведомление об успешном обновлении.

4. **Удаление ссылки**
   - **Метод**: `DELETE`
   - **URL**: `/api/link/delete`
   - **Описание**: Удаляет ссылку по ее короткому или длинному URL.
   - **Тело запроса**: 
     ```json
     {
       "shortUrl": "string",
       "originalUrl": "string",
       "userId": "UUID"
     }
     ```
   - **Ответ**: Уведомление об успешном удалении.

5. **Редирект по короткой ссылке**
   - **Метод**: `GET`
   - **URL**: `/api/link`
   - **Описание**: Перенаправляет пользователя на оригинальный URL по короткой ссылке.
   - **Тело запроса**:
     ```json
     {
       "shortLink": "string"
     }
     ```
   - **Ответ**: Редирект на оригинальный URL.

---

## **Уведомления** (`/api/notifications`)

1. **Получение уведомлений пользователя**
   - **Метод**: `GET`
   - **URL**: `/api/notifications/{userId}`
   - **Описание**: Возвращает список всех уведомлений для указанного пользователя.
   - **Параметры**:
     - `userId` — UUID пользователя.
   - **Ответ**: Список уведомлений.
