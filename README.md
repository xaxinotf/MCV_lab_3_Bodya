Тести розробляються для однієї з лабораторних з курсу Системне програмування, або іншого проекту не меншої складності.
Мова програмування не обов’язково Java.
При виконанні роботи іншою мовою програмування потрібно використовувати аналогічний фреймворк (наприклад nUnit для
C# в першій лабораторній).

Використовуючи Mockito або інший імітаційний (мок) фреймворк потрібно написати тести, які б використовували мок об’єкти.
Для цього потрібно в проекті, для якого задаються тести провести рефакторинг — додати інтерфейс, винести зчитування з диску, доступ до БД та ін. в окремий клас, та утворити “зазор” для використання мок об’єктів.

Написані тести повинні відповідати наступним вимогам:
для мок об’єкту задається принаймні 3 сценарії, один з яких буде стосуватись обробки виключень;
має бути реалізована перевірка того, що метод було викликано певну кількість разів;
продемонстрована ініціалізація за допомогою анотацій (@Mock @InjectMocks);
має бути використаний частковий мок об’єкт @Spy.
один сценарій за якого мок об’єкт має згенерувати виключення.
