# JDBC_education

## На чистом JDBC создать таблицы:

- User (id, name, surname)
- Account (id, accountNumber, type, userId, openDate)
- Пользователь и его банковские счета. Type; debit или credit. OpenDate: дата открытия.

## Получить и вывести на экран:

- Имя, Фамилия пользователя, accountNumber, type, openDate, где фамилия пользователя Ivanov (в ResultSet должно быть
  несколько строк)
- Имя, Фамилия пользователя, accountNumber, type, openDate, где фамилия пользователя Ivanov и type аккаунта = credit
- Удалить пользователя и все его данные по фамилии и имени: Petrov Boris. Вывести сообщение об успешной операции.
- Изменить фамилию пользователю с Ivanova на Mashkova. Вывести сообщение об успешной операции и количестве измененных
  строк.

## Как запустить
- скопировать проект.
- В каталоге с Docker-compose.yaml открыть консоль и выполнить 
  docker-compse build
