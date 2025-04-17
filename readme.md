# Kafka Connect RecordBuilder for Windows-1251 MQ messages

Кастомный `RecordBuilder` для IBM MQ Source Connector, преобразующий сообщения в кодировке Windows-1251 в строки UTF-8 и публикующий их в Kafka.

## Сборка

### 1. Установить зависимость `kafka-connect-mq-source` в локальный Maven-репозиторий

```bash
mvn install:install-file \
  -Dfile=lib/kafka-connect-mq-source-2.3.0-jar-with-dependencies.jar \
  -DgroupId=com.ibm.eventstreams \
  -DartifactId=kafka-connect-mq-source \
  -Dversion=2.3.0 \
  -Dpackaging=jar
```

### 2. Собрать проект
```
mvn clean package
```
Готовый `.jar` будет доступен по пути:
```
target/kafka-cp1251-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Использование в Kafka Connect
1. Скопируйте `.jar` в директорию `plugin.path`, например:
```
/opt/kafka/connectors/cp1251-builder/
```
2. Убедитесь, что в конфигурации Kafka Connect:
```
plugin.path=/opt/kafka/connectors
```
3. Укажите в конфигурации Source Connector:
```
"mq.record.builder": "custom.builder.Cp1251RecordBuilder"
```
## Требования
- Java 11+
- Maven 3.6+
- Kafka Connect
- IBM MQ Source Connector (встроен в `lib/`)

## Используемые компоненты и авторские права

Данный проект расширяет функциональность официального IBM MQ Connector для Kafka Connect.

### IBM MQ Connectors for Kafka Connect

- **Источник**: https://github.com/ibm-messaging/kafka-connect-mq-source
- **Лицензия**: [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)  
- **Автор**: IBM Messaging


### Apache Kafka Connect

- **Источник**: https://github.com/apache/kafka  
- **Лицензия**: [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)  
- **Автор**: Apache Software Foundation (ASF)


В проект включён файл:
```
lib/kafka-connect-mq-source-2.3.0-jar-with-dependencies.jar
```

Он предоставляется **исключительно для удобства локальной сборки**, не модифицирован, и может быть также загружен напрямую с [официальной страницы релизов IBM](https://github.com/ibm-messaging/kafka-connect-mq-source/releases).
> Использование производится в соответствии с условиями лицензии Apache 2.0, включая указание оригинального источника и сохранение авторства.