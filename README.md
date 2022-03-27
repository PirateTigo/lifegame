# Игра "Жизнь"

## Сборка и запуск приложения
Используйте для сборки и запуска следующие инструменты:
- Apache Maven 3.6+
- JDK 11
- Java 11
- JavaFX SDK 11+

Выполните команду в директории проекта:
`mvn clean package`

Результат сборки находится в директории `target`

Используйте следующую команду для запуска приложения:
`java -p ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml -jar lifegame-<VERSION>-jar-with-dependencies.jar`

**ОБРАТИТЕ ВНИМАНИЕ!** \
VERSION необходимо заменить на номер версии приложения перед выполнением команды

## Разработка
### Запуск приложения из IDE в режиме разработки
Используйте следующие VM options:
- `-p ${PATH_TO_FX}` - системная переменная должна указывать на JavaFX SDK \
###### Пример для Windows:
`C:/Users/user1/Tools/javafx-sdk-17.0.2/lib`
###### Пример для Linux:
`/home/user1/Tools/javafx-sdk-17.0.2/lib`
###### Пример для MacOS:
`/Users/user1/Tools/javafx-sdk-17.0.2/lib`
- `--add-modules javafx.controls,javafx.fxml` - модули JavaFX необходимы для запуска приложения Java 11
