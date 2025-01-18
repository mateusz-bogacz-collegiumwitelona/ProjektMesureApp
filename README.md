# Projekt zaliczeniowy na przedmiot Projektowanie i programowanie obiektowe

Jest to prosta aplikacja do monitorowania ciśnienia tętniczego.

# Dokumentacja Aplikacji Bieda Ciśnienie

## Spis treści
1. [Wprowadzenie](#wprowadzenie)
2. [Architektura Systemu](#architektura-systemu)
3. [Główne Funkcjonalności](#główne-funkcjonalności)
4. [Struktura Projektu](#struktura-projektu)
5. [Szczegóły Implementacji](#szczegóły-implementacji)
6. [Obsługa Błędów](#obsługa-błędów)
7. [Format Plików](#format-plików)
8. [Interfejs Użytkownika](#interfejs-użytkownika)

## Wprowadzenie
Bieda Ciśnienie to aplikacja desktopowa napisana w Java/Swing, służąca do monitorowania i analizy pomiarów ciśnienia krwi. Program umożliwia użytkownikom zapisywanie, przeglądanie i analizowanie pomiarów ciśnienia oraz eksportowanie danych do różnych formatów plików.

### Główne cechy
- Zapisywanie pomiarów ciśnienia (górnego, dolnego) i pulsu
- Przeglądanie historii pomiarów
- Sortowanie i analiza danych
- Eksport danych do formatów CVE i TXT
- Intuicyjny interfejs graficzny

## Architektura Systemu

### Wzorce projektowe
1. **Builder Pattern**
   - Implementacja: `MeasurementBuilder`
   - Cel: Bezpieczne tworzenie obiektów `Measurement`

2. **Factory Pattern**
   - Implementacja: `FrameFactory`
   - Cel: Tworzenie różnych typów okien aplikacji

3. **Interface Segregation**
   - Implementacja: `FileExporter`, `MeasurementStorage`, `MeasureOperations`
   - Cel: Rozdzielenie odpowiedzialności i ułatwienie rozszerzania funkcjonalności

### Hierarchia klas
```
AbstractFrame
├── MainFrame
├── AddMeasurementFrame
├── ViewMeasurementsFrame
└── SaveOptionsFrame

MeasurementException
├── ValidationException
│   └── EmptyFieldException
└── FileOperationException
```

## Główne Funkcjonalności

### 1. Zarządzanie pomiarami
- **Dodawanie pomiarów**
  - Klasa: `AddMeasurementFrame`
  - Walidacja danych wejściowych
  - Automatyczne zapisywanie do pliku

- **Przeglądanie pomiarów**
  - Klasa: `ViewMeasurementsFrame`
  - Sortowanie według różnych kryteriów
  - Obliczanie średnich wartości

### 2. Operacje na plikach
- **Format CVE**
  - Separator: średnik (;)
  - Format daty: dd.MM.yyyy HH:mm:ss
  - Struktura: data;ciśnienie_górne;ciśnienie_dolne;puls

- **Format TXT**
  - Format czytelny dla człowieka
  - Zawiera nagłówki i separatory

## Struktura Projektu

### Pakiety
1. **GUI**
   - Zawiera wszystkie klasy interfejsu użytkownika
   - Bazuje na bibliotece Swing

2. **Measure**
   - Klasy związane z pomiarami
   - Implementacja logiki biznesowej

3. **FileOperation**
   - Obsługa operacji plikowych
   - Implementacje eksporterów

4. **Exceptions**
   - Własna hierarchia wyjątków
   - Obsługa błędów specyficznych dla aplikacji

5. **Interfaces**
   - Definicje interfejsów
   - Zapewnienie luźnego powiązania między komponentami

## Szczegóły Implementacji

### Klasy bazowe i interfejsy

#### AbstractFrame
```java
protected abstract void initComponents();
protected abstract void setupListeners();
```
- Bazowa klasa dla wszystkich okien
- Zapewnia wspólną funkcjonalność UI

#### MeasurementStorage
```java
void save(Measurement measurement);
List<Measurement> loadAll();
void exportTo(File file, FileExporter exporter);
```
- Interfejs dla operacji przechowywania pomiarów

### Obsługa danych

#### Measurement
- Niemutowalna klasa reprezentująca pomiar
- Budowana przez `MeasurementBuilder`
- Zawiera:
  - Timestamp
  - Ciśnienie górne
  - Ciśnienie dolne
  - Puls

## Obsługa Błędów

### Hierarchia wyjątków
1. **MeasurementException**
   - Bazowa klasa dla wszystkich wyjątków aplikacji

2. **ValidationException**
   - Błędy walidacji danych
   - Przechowuje informacje o nieprawidłowych polach

3. **EmptyFieldException**
   - Specyficzny przypadek ValidationException
   - Używany gdy pole jest puste

4. **FileOperationException**
   - Błędy operacji plikowych
   - Zawiera informacje o typie operacji i ścieżce pliku

## Format Plików

### CVE (Custom Value Export)
```
dd.MM.yyyy HH:mm:ss;systolic;diastolic;pulse
```
Przykład:
```
08.01.2025 20:23:56;354;456;543
```

### TXT
```
Pomiary ciśnienia
=================

Data: dd.MM.yyyy HH:mm:ss
Ciśnienie górne: XXX
Ciśnienie dolne: XXX
Puls: XXX
-------------------------
```

## Interfejs Użytkownika

### Główne komponenty

#### MainFrame
- Menu główne aplikacji
- Nawigacja między funkcjami
- Ciemny motyw kolorystyczny

#### AddMeasurementFrame
- Formularz dodawania pomiarów
- Walidacja w czasie rzeczywistym
- Automatyczne przechodzenie między polami

#### ViewMeasurementsFrame
- Tabela pomiarów
- Opcje sortowania
- Obliczanie statystyk

#### SaveOptionsFrame
- Wybór formatu eksportu
- Wybór lokalizacji pliku
- Potwierdzenie zapisu

### Stylizacja
- Ciemny motyw
- Niebieskie akcenty
- Responsywny układ
- Intuicyjna nawigacja

## Szczegółowy opis klas i funkcji

### Pakiet GUI

#### AbstractFrame
Abstrakcyjna klasa bazowa dla wszystkich okien aplikacji.
- **Konstruktor(String title)**
  - Tworzy nowe okno z podanym tytułem
  - Inicjalizuje podstawowe ustawienia okna
- **createButton(String text)**
  - Tworzy przycisk z zadanym tekstem i stylizacją
  - Dodaje efekty hover
- **showError(String title, String message)**
  - Wyświetla okno dialogowe z błędem
- **showSuccess(String message)**
  - Wyświetla okno dialogowe z sukcesem
- **addComponent()**
  - Dodaje komponenty do układu GridBag
- **show()**
  - Wyświetla okno
- **close()**
  - Zamyka okno

#### MainFrame
Główne okno aplikacji.
- **initComponents()**
  - Inicjalizuje wszystkie komponenty UI
  - Ustawia ciemny motyw
  - Tworzy menu nawigacyjne
- **setupListeners()**
  - Konfiguruje obsługę przycisków nawigacji
- **createButton(String text)**
  - Przeciążona metoda tworzenia stylizowanych przycisków
- **createBJComboBox(String text)**
  - Tworzy stylizowane rozwijane menu

#### AddMeasurementFrame
Okno dodawania nowego pomiaru.
- **initComponents()**
  - Tworzy formularz z polami wprowadzania
- **setupListeners()**
  - Konfiguruje obsługę przycisków i pól
- **saveMeasurement()**
  - Waliduje i zapisuje nowy pomiar
- **validateFields()**
  - Sprawdza poprawność wprowadzonych danych
- **createMeasurement()**
  - Tworzy obiekt pomiaru z wprowadzonych danych
- **clearFields()**
  - Czyści pola formularza

#### ViewMeasurementsFrame
Okno przeglądania pomiarów.
- **initComponents()**
  - Tworzy tabelę pomiarów i kontrolki
- **setupListeners()**
  - Konfiguruje obsługę sortowania i filtrowania
- **loadMeasurements()**
  - Wczytuje pomiary z pliku
- **updateTable()**
  - Aktualizuje widok tabeli
- **sortMeasurements(String criteria)**
  - Sortuje pomiary według wybranego kryterium
- **showAverages()**
  - Oblicza i wyświetla średnie wartości

#### SaveOptionsFrame
Okno opcji zapisu.
- **initComponents()**
  - Tworzy przyciski eksportu
- **setupListeners()**
  - Konfiguruje obsługę eksportu
- **saveToCVE()**
  - Eksportuje dane do formatu CVE
- **saveToTXT()**
  - Eksportuje dane do formatu TXT

### Pakiet Measure

#### Measurement
Klasa reprezentująca pojedynczy pomiar.
- **Konstruktor(MeasurementBuilder builder)**
  - Tworzy niemutowalny obiekt pomiaru
- **getTimestamp()/getSystolic()/getDiastolic()/getPulse()**
  - Gettery dla wartości pomiaru
- **toString()**
  - Formatuje pomiar do postaci tekstowej

#### MeasurementBuilder
Builder do tworzenia obiektów Measurement.
- **withTimestamp()/withSystolic()/withDiastolic()/withPulse()**
  - Metody ustawiające wartości
- **build()**
  - Tworzy i waliduje obiekt Measurement
- **validate()**
  - Sprawdza poprawność wszystkich wartości

### Pakiet FileOperation

#### CVEStorage
Implementacja zapisu/odczytu w formacie CVE.
- **save(Measurement measurement)**
  - Zapisuje pojedynczy pomiar
- **loadAll()**
  - Wczytuje wszystkie pomiary
- **exportTo(File file, FileExporter exporter)**
  - Eksportuje dane do wybranego formatu
- **createDirectoryIfNeeded()**
  - Tworzy katalog jeśli nie istnieje

#### CVEExporter
Eksporter do formatu CVE.
- **export(List<Measurement> measurements, File file)**
  - Eksportuje listę pomiarów do pliku CVE

#### TXTExporter
Eksporter do formatu TXT.
- **export(List<Measurement> measurements, File file)**
  - Eksportuje listę pomiarów do pliku TXT

### Pakiet Exceptions

#### MeasurementException
Bazowa klasa wyjątków aplikacji.
- Konstruktory z message i cause

#### ValidationException
Wyjątek walidacji danych.
- **getInvalidFields()**
  - Zwraca mapę nieprawidłowych pól

#### EmptyFieldException
Wyjątek pustego pola.
- Konstruktor przyjmujący nazwę pustego pola

#### FileOperationException
Wyjątek operacji plikowych.
- **getFilePath()/getOperationType()**
  - Zwracają szczegóły błędu operacji

### Pakiet Interfaces

#### MeasurementStorage
Interfejs przechowywania pomiarów.
- **save(Measurement measurement)**
- **loadAll()**
- **exportTo(File file, FileExporter exporter)**

#### FileExporter
Interfejs eksportu danych.
- **export(List<Measurement> measurements, File file)**

#### MeasureOperations
Interfejs operacji na pomiarach.
- **saveMeasure(String systolic, String diastolic, String pulse)**
- **loadMeasures()**
- **saveToCustomCVE(File file)**
- **saveToCustomTXT(File file)**

---

Wykonali:
- Mateusz Bogacz-Drewniak
- Michał Żmuda 
- Mateusz Chimkowski

*Collegium Witelona Uczelnia Państwowa, 2025*