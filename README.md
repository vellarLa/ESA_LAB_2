# Лабораторная работа 2 (Spring)
Приложение лабораторной работы 1 было разработано для посетителей кинотеатра, которые хотят купить билет на сеанс онлайн. 

Это же приложение нацелено на **администраторов** (тех кто продает билет и регистрирует его в системе).

Кроме того, администраторам в этом случае предоставляются **расширенные права по управлению фильмами в прокате и расписанием сеансов**.

> Приложение реализовано с использованием фреймфорка Spring Boot, являющегося частью фреймворка Spring.
## PostgreSQL
В качестве СУБД была использована PostgreSQL. При установке postgres на локальный компьютер была создана локальная база данных, она и использовалась в работе.
Параметры подключения к базе данных и системные настройки проложения приведены в файле [application.proporties](https://github.com/vellarLa/ESA_LAB_2/blob/master/src/main/resources/application.properties)
```bash
spring.datasource.hikari.connectionTimeout=20000
spring.datasource.hikari.maximumPoolSize=5

spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=123456

spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto = update
spring.jpa.properties.hibernate.show_sql=true

spring.mvc.hiddenmethod.filter.enabled=true
server.port=8081
```
## Data layer
> Для сокрытия деталей реализации хранения сущностей был смоделирован дополнительный слой DTO.
Для работы с базой данный был смоделирован DAO слой.
> 
**Предметная область - кинотеатр**. В качестве сущностей взяты:
- film - фильм в прокате
- timetable - расписание (зал, фильм, время)
- ticket - билет

Схема базы данных:

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/aefff4f1-d60c-46ec-b059-c5e47b0153c6" width="200">

## Business layer
Логика приложения реализована в Service классах. В бизнес-слое реализована логика поиска свободных мест (ряд и место) с учетом выбора сеанса.

Пример сервисного класса для работы с расписанием.

```bash
@Service
@RequiredArgsConstructor
public class TimetableServiceImpl implements TimetableService {
    private final TimetableRepository timetableRepository;
    @Override
    public void save(TimetableDto timetableDto) {
        timetableRepository.save(timetableDto.toEntity());
    }
    @Override
    public void deleteById(Long id) {
        timetableRepository.deleteById(id);
    }
    @Override
    public void deleteAll() {
        timetableRepository.deleteAll();
    }
    @Override
    public TimetableDto findById(Long id) {
        return timetableRepository.findFirstById(id).toDto();
    }
    @Override
    public List<TimetableDto> findAll() {
        return timetableRepository.findAll().stream().map(Timetable::toDto).collect(Collectors.toList());
    }
    @Override
    public List<TimetableDto> findAllByFilm(FilmDto filmDto) {
        return timetableRepository.findAllByFilm_Id(filmDto.getId())
                .stream().map(Timetable::toDto)
                .collect(Collectors.toList());
    }
}
```
## View layer
За обработку запросов отвечают контроллеры, пользовательский интерфейс реализован на html страницах.
# Демонстрация работоспособности
Стартовая страница. На ней можно выбрать, какую сущность будем модифицировать.

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/765f04da-a511-4aa6-97d0-068dcca12f3a" width="300">

### Фильмы 
На этой странице мы видим список фильмов, находящихся в прокате и их параметры. Здесь же есть форма для создания 
нового фильма, выходящего в прокат в кинотеатре.

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/6e67cc69-815c-48d6-808a-8708aab5266f" width="600">

Создание фильма:

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/0727deec-eb16-47cb-a557-89a2fa351b44" width="600">
<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/1a7d355a-1676-4700-9bf7-800a0e62ff0a" width="600">

Изменение фильма. При нажатии кнопки "update" открывается форма для редактирования параметров фильма

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/622a9d10-d00b-4ab4-bb72-41ce12436f46" width="200">
<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/e8929f97-1690-4731-82ff-ab2b3d938fc9" width="700">

При нажатии на кнопку "delete" фильм удаляется. **Реализовано каскадное удаление. При удалении фильма, удаляются все связанные с ним сеансы и купленные билеты.**

### Расписание
На этой странице мы видим список сеансов. Здесь же есть форма для создания нового сеанса.

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/656f698d-637a-4bfe-8b9c-09257afd4073" width="600">

В форме для создания сеанса в выпадающем списке отображаются все фильмы, находящиеся в прокате.

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/46e09d81-ed7f-47c3-ae60-da3f2ef972e4" width="400">

Изменение сеанса. При нажатии кнопки "update" открывается форма для редактирования параметров сеанса

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/163693d3-7fbf-4d9d-a25e-6df816a0a8f4" width="200">

При нажатии на кнопку "delete" сеанс удаляется. **Реализовано каскадное удаление. При удалениисеанса, удаляются все купленные на него билеты.**

### Билеты
На этой странице мы видим список купленных билетов. Здесь же есть форма для бронирования билета.

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/cfcf2823-04dc-447c-8141-f9593bffbb27" width="600">

В форме для бронирования билета в выпадающем списке отображаются все фильмы, находящиеся в прокате. 

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/fca8040a-20da-4a38-93cb-3e6b66b4d1ce" width="400">

При выборе фильма в следующем select будут доступны только его сеансы.

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/5ae3a9ef-f8d1-4d3e-803d-64fd5c580aea" width="600">



<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/035d2984-a4f5-4b3e-998f-bdd17764cabd" width="400">

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/4b2e9d1b-88dc-423f-8876-d2ac9e4575d2" width="400">

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/2b91b010-eb7b-4ffe-94c0-8e031aa6d29c" width="400">

Также при изменении значений в select для расписания в select для выбора мест автоматически подтягиваются доступные
для данного сеанса места (инициализация тоже происходит только доступными)

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/70d2d8bc-5ce6-443a-a059-0bf4caa2bfa5" width="600">

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/91800430-b17e-4649-916a-6ee0b05f88a2" width="600">

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/2e59b8e6-2ec6-45ee-a7e9-1c6ea71c73b2" width="600">


Изменение билета. При нажатии кнопки "update" открывается форма для редактирования параметров билета. В этой форме
весь каскад обновлений также сохраняется.

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/0b3b8fa4-a85a-4a99-a1df-579771722e80" width="200">

До обновления
 
<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/f0ca7ac1-4601-4b87-b2cc-468bc3503294" width="600">

После обновления (обновили наличие льгов - поменялась стоимость)

<img src="https://github.com/vellarLa/ESA_LAB_2/assets/83453185/7a0e16fb-c4f6-4b8a-8ad5-af7bbd715d19" width="600">

