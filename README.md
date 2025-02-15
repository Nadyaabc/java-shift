# Employee Manager

Программа для обработки данных о сотрудниках и менеджерах.

## Требования
- Java 17
- Maven 3.8.6

## Сборка и запуск

1. Клонируйте репозиторий:
   ```bash
   git clone <репозиторий>
   cd java-shift
2. Соберите проект:
   ```bash
   mvn clean package
3. Запустите программу:
   ```bash
   java -jar target/shift-1.0-SNAPSHOT.jar --input=input.txt --output=console --sort=name --order=asc

### Параметры командной строки
--input=<файл>: Путь к входному файлу (по умолчанию input.txt).

--output=<console|file>: Режим вывода (по умолчанию console).

--path=<файл>: Путь к выходному файлу (если --output=file).

--sort=<name|salary>: Параметр сортировки.

--order=<asc|desc>: Порядок сортировки.
