# ConverRESTful
## Описание
Это RESTful приложение, которое позволит пользователю конвертировать число в строку на английском языке и наоборот.

## Установка:
Вам необходимо иметь у себя postgreSQL для подключение к базе данных.
Вся необходимая информация о подключении хранится в файле application.properties
Необходимо будет прописать путь к своей БД и указать логин и пароль.

Скопируйте репозиторий с проектом к себе и запустите.

## Запуск:
1. Откройте проект
2. Запустите ConverResTfulApplication.
## Авторизация

Для запуска приложения требуется авторизация. В процессе разработки созданы следующие учетные записи:

- Логин: "admin", Пароль: "admin"
- Логин: "batman", Пароль: "test"

Вы можете использовать учетные данные, передав их через параметры `-u` при запуске curl. Например:

```
curl "http://localhost:8080/convert?type=StringToNumber&value=zero" -u "login:password"
```
## Использование
Пользователь может отправлять GET запрос, которые принимает следующие параметры:
- type: Тип перевод: *StringToNumber* или *NumberToString* 
- value:  - Значение, которое нужно преобразовать. Оно зависит от типа перевода.
## Пример корректных запросов:
### Из строки в число
```  
curl "http://localhost:8080/convert?type=StringToNumber&value=zero" -u "admin:admin"
```
http-статус : ***200***

Response Body: 
```
{"result":"0"}
```

```
curl "http://localhost:8080/convert?type=StringToNumber&value=two" -u "admin:admin"
```

http-статус : ***200***

Response Body: 
```
{"result":"2"}
```


```
curl "http://localhost:8080/convert?type=StringToNumber&value=minus%20two" -u "admin:admin"

```
http-статус : ***200***

Response Body: 
```
{"result":"-2"}
```

```
curl "http://localhost:8080/convert?type=StringToNumber&value=one%20hundred%20twenty%20three%20million%20four%20hundred%20fifty%20six%20thousand%20seven%20hundred%20eighty%20nine" -u "admin:admin"
```
http-статус : ***200***

Response Body: 
```
{"result":"123456789"}
```


### Из числа в строку
```
curl "http://localhost:8080/convert?type=NumberToString&value=0" -u "admin:admin"
```
http-статус : ***200***

Response Body: 
```
{"result":"zero"}
```

```
curl "http://localhost:8080/convert?type=NumberToString&value=987654321" -u "admin:admin"
```

http-статус : ***200***

Response Body: 
```
{"result":"nine hundred eighty seven million six hundred fifty four thousand three hundred twenty one"}
```

```
curl "http://localhost:8080/convert?type=NumberToString&value=-120005" -u "admin:admin"
```

http-статус : ***200***

Response Body: 
```
{"result":"minus one hundred twenty  thousand five"}
```


## Пример некорректных запросов, которые вернут ошибку.
### Если при переводе из строки в число, в строке встретилось неправильно написанное слово:
```
curl "http://localhost:8080/convert?type=StringToNumber&value=oneh" -u "admin:admin"
```

http-статус : ***400***

Response Body: 
```
{"descriptionError":"Incorrect word oneh"}
```

```
curl "http://localhost:8080/convert?type=StringToNumber&value=minus%20two%20asd" -u "admin:admin"
```

http-статус : ***400***

Response Body: 
```
{"descriptionError":"Incorrect word asd"}
```
### Если при переводе из числа в строку число превышает максимально допустимое значение(999999999999)
```
curl "http://localhost:8080/convert?type=NumberToString&value=1000000000000" -u "admin:admin"
```

http-статус : ***400***

Response Body: 
```
{"descriptionError":"Number is too large"}
```

### Пример неправильного указания type 
```
curl "http://localhost:8080/convert?type=NumberToSlovo&value=5" -u "admin:admin"
```

http-статус : ***400***

Response Body: 
```
{"descriptionError":"Failed to convert value of type 'java.lang.String' to required type 'ru.leskov.converrestful.controllers.ConvertType'; Failed to convert from type [java.lang.String] to type [@org.springframework.web.bind.annotation.RequestParam ru.leskov.converrestful.controllers.ConvertType] for value [NumberToSlovo]"}
```
      
