# Sports Team Management System

Kompleksowy system do zarządzania drużyną sportową, obejmujący zarządzanie zawodnikami, statystykami, treningami, planowaniem zespołu i nieobecnościami.

## Spis treści

- [Opis projektu](#opis-projektu)
- [Technologie](#technologie)
- [Wymagania](#wymagania)
- [Instalacja](#instalacja)
- [Konfiguracja](#konfiguracja)
- [Uruchomienie](#uruchomienie)
- [Struktura projektu](#struktura-projektu)
- [API Endpoints](#api-endpoints)
- [Role i uprawnienia](#role-i-uprawnienia)
- [Dokumentacja API](#dokumentacja-api)
- [Bezpieczeństwo](#bezpieczeństwo)
- [Testy](#testy)
- [Wdrożenie](#wdrożenie)
- [Współpraca](#współpraca)
- [Licencja](#licencja)

## Opis projektu

Sports Team Management System to kompleksowe rozwiązanie backendowe (REST API) zaprojektowane do zarządzania wszystkimi aspektami drużyny sportowej, w tym:

- Zarządzanie zawodnikami i ich statystykami
- Planowanie treningów i monitorowanie frekwencji
- Zarządzanie meczami i statystykami meczowymi
- Skautingowe raporty i analizy wydajności
- Zarządzanie kontraktami i budżetem zespołu
- Obsługa nieobecności zawodników

System obsługuje różne role użytkowników, takie jak właściciele zespołów, trenerzy, skauci, zawodnicy i kibice, z odpowiednim poziomem dostępu i funkcjonalności dla każdej roli.

## Technologie

- Java 17
- Spring Boot 3.1.x
- Spring Data JPA
- Spring Security
- Hibernate
- MySQL 8.0
- Maven
- Swagger/OpenAPI
- JWT Authentication
- JUnit 5 & Mockito

## Wymagania

- Java 17 lub wyższy
- Maven 3.8.x
- MySQL 8.0
- Docker & Docker Compose (opcjonalnie)

## Instalacja

1. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/username/sports-team-manager.git
   cd sports-team-manager
   ```

2. Zbuduj projekt:
   ```bash
   mvn clean install
   ```

## Konfiguracja

### Baza danych

1. Utwórz bazę danych MySQL:
   ```sql
   CREATE DATABASE sports_team_manager;
   CREATE USER 'sports_user'@'%' IDENTIFIED BY 'yourPassword';
   GRANT ALL PRIVILEGES ON sports_team_manager.* TO 'sports_user'@'%';
   FLUSH PRIVILEGES;
   ```

2. Skonfiguruj połączenie z bazą danych w `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/sports_team_manager?useSSL=false&serverTimezone=UTC
   spring.datasource.username=sports_user
   spring.datasource.password=yourPassword
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   ```

### JWT

Skonfiguruj JWT w `application.properties`:
```properties
jwt.secret=yourSecretKeyHereMakeSureItIsLongAndComplex
jwt.expiration=86400000
```

## Uruchomienie

### Lokalnie

```bash
mvn spring-boot:run
```

### Za pomocą Docker

```bash
docker-compose up -d
```

Po uruchomieniu, API będzie dostępne pod adresem: `http://localhost:8080/api/`

## Struktura projektu

Projekt jest zorganizowany zgodnie z najlepszymi praktykami Spring Boot i wzorcem architektonicznym Controller-Service-Repository:

```
src/main/java/com/teammanager/
├── config/          # Konfiguracja aplikacji, Spring Security, Swagger
├── controller/      # Kontrolery REST API
├── dto/             # Obiekty transferu danych
├── exception/       # Obsługa wyjątków
├── model/           # Encje bazodanowe
├── repository/      # Repozytoria JPA
├── service/         # Logika biznesowa
├── security/        # Implementacja JWT i bezpieczeństwa
└── util/            # Klasy narzędziowe
```

## API Endpoints

### Uwierzytelnianie i użytkownicy

- `POST /api/auth/login` - Logowanie i generowanie tokenu JWT
- `POST /api/auth/register` - Rejestracja nowego użytkownika
- `GET /api/users` - Pobierz wszystkich użytkowników (tylko admin)
- `GET /api/users/{id}` - Pobierz użytkownika według ID
- `PUT /api/users/{id}` - Zaktualizuj użytkownika
- `DELETE /api/users/{id}` - Usuń użytkownika (tylko admin)

### Drużyny

- `GET /api/teams` - Pobierz wszystkie drużyny
- `GET /api/teams/{id}` - Pobierz drużynę według ID
- `GET /api/teams/{id}/roster` - Pobierz skład drużyny
- `GET /api/teams/{id}/salary` - Pobierz przegląd wynagrodzeń drużyny
- `POST /api/teams` - Utwórz nową drużynę (właściciel, GM)
- `PUT /api/teams/{id}` - Zaktualizuj drużynę (właściciel, GM)
- `DELETE /api/teams/{id}` - Usuń drużynę (tylko właściciel)

### Zawodnicy

- `GET /api/players` - Pobierz wszystkich zawodników
- `GET /api/players/{id}` - Pobierz zawodnika według ID
- `GET /api/players/search?name=xxx` - Wyszukaj zawodników po nazwie
- `GET /api/players/team/{teamId}` - Pobierz zawodników według drużyny
- `POST /api/players` - Utwórz nowego zawodnika (GM, właściciel)
- `PUT /api/players/{id}` - Zaktualizuj zawodnika (GM, właściciel, trener)
- `DELETE /api/players/{id}` - Usuń zawodnika (GM, właściciel)

### Statystyki

- `GET /api/statistics/players` - Pobierz wszystkie statystyki zawodników
- `GET /api/statistics/players/{id}` - Pobierz statystyki zawodnika
- `GET /api/statistics/players/compare?player1Id=x&player2Id=y` - Porównaj zawodników
- `GET /api/statistics/league/averages` - Pobierz średnie ligowe
- `POST /api/statistics/players` - Dodaj statystyki zawodnika (analityk zespołu, trener)
- `PUT /api/statistics/players/{id}` - Zaktualizuj statystyki zawodnika (analityk zespołu, trener)

### Treningi

- `GET /api/trainings` - Pobierz wszystkie treningi (dla zalogowanego użytkownika)
- `GET /api/trainings/{id}` - Pobierz trening według ID
- `GET /api/trainings/team/{teamId}` - Pobierz treningi zespołu
- `GET /api/trainings/attendance/team/{teamId}` - Pobierz statystyki uczestnictwa w treningach
- `POST /api/trainings` - Utwórz nowy trening (trener)
- `PUT /api/trainings/{id}` - Zaktualizuj trening (trener)
- `DELETE /api/trainings/{id}` - Usuń trening (trener)
- `POST /api/trainings/{id}/attendance` - Zapisz obecność na treningu (trener)

### Mecze

- `GET /api/matches` - Pobierz wszystkie mecze
- `GET /api/matches/{id}` - Pobierz mecz według ID
- `GET /api/matches/team/{teamId}` - Pobierz mecze zespołu
- `GET /api/matches/upcoming` - Pobierz nadchodzące mecze
- `POST /api/matches` - Utwórz nowy mecz (GM, trener)
- `PUT /api/matches/{id}` - Zaktualizuj mecz (GM, trener)
- `DELETE /api/matches/{id}` - Usuń mecz (GM)
- `POST /api/matches/{id}/statistics` - Dodaj statystyki meczu (analityk, trener)

### Skauting

- `GET /api/scouting/reports` - Pobierz wszystkie raporty skautingowe (skaut)
- `GET /api/scouting/reports/{id}` - Pobierz raport według ID (skaut)
- `GET /api/scouting/reports/player/{playerId}` - Pobierz raporty dla zawodnika (skaut)
- `POST /api/scouting/reports` - Utwórz nowy raport (skaut)
- `PUT /api/scouting/reports/{id}` - Zaktualizuj raport (skaut)
- `DELETE /api/scouting/reports/{id}` - Usuń raport (skaut)
- `GET /api/scouting/ncaa` - Pobierz statystyki zawodników NCAA (skaut)

### Nieobecności

- `GET /api/absences` - Pobierz wszystkie nieobecności (dla managera/trenera)
- `GET /api/absences/{id}` - Pobierz nieobecność według ID
- `GET /api/absences/player/{playerId}` - Pobierz nieobecności zawodnika
- `POST /api/absences` - Zgłoś nieobecność (zawodnik, trener)
- `PUT