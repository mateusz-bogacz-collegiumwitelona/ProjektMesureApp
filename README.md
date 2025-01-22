# Dokumentacja Programu "Bieda Ciśnienie"

Bieda Ciśnienie to aplikacja do monitorowania ciśnienia krwi. Pozwala na zapisywanie, przeglądanie i analizowanie pomiarów ciśnienia oraz eksport danych.

## 1. Struktura Projektu

### 1.1 Pakiety
- `GUI` - Zawiera komponenty interfejsu użytkownika
- `Measure` - Zawiera klasy związane z pomiarami ciśnienia
- `Exceptions` - Zawiera klasy wyjątków
- `FileOperation` - Zawiera klasy odpowiedzialne za operacje na plikach
- `Interfaces` - Zawiera interfejsy definiujące kontrakty

## 2. Główne Komponenty

### 2.1 Klasa Main
**Plik**: `Main.java`
- Punkt wejściowy aplikacji
- Konfiguruje wygląd systemowy (Look and Feel)
- Uruchamia główne okno aplikacji w bezpiecznym wątku EDT (Event Dispatch Thread)

### 2.2 Model Danych

#### 2.2.1 Klasa Measurement
**Plik**: `Measurement.java`
- Reprezentuje pojedynczy pomiar ciśnienia
- **Pola**:
  - `timestamp` - Data i czas pomiaru (LocalDateTime)
  - `systolic` - Ciśnienie górne (int)
  - `diastolic` - Ciśnienie dolne (int)
  - `pulse` - Puls (int)
- Implementuje wzorzec Builder poprzez MeasurementBuilder

#### 2.2.2 Klasa MeasurementBuilder
**Plik**: `MeasurementBuilder.java`
- Implementuje wzorzec projektowy Builder
- **Metody**:
  - `withTimestamp()` - Ustawia datę pomiaru
  - `withSystolic()` - Ustawia ciśnienie górne
  - `withDiastolic()` - Ustawia ciśnienie dolne
  - `withPulse()` - Ustawia puls
  - `build()` - Tworzy i waliduje obiekt Measurement
- **Walidacja**:
  - Sprawdza czy wszystkie wartości są dodatnie
  - Zgłasza ValidationException w przypadku błędnych danych

### 2.3 Interfejsy

#### 2.3.1 FileExporter
**Plik**: `FileExporter.java`
- Definiuje kontrakt dla eksportu pomiarów do plików
- **Metody**:
  - `export()` - Eksportuje listę pomiarów do pliku

#### 2.3.2 MeasurementStorage
**Plik**: `MeasurementStorage.java`
- Definiuje kontrakt dla przechowywania pomiarów
- **Metody**:
  - `save()` - Zapisuje pojedynczy pomiar
  - `loadAll()` - Wczytuje wszystkie pomiary
  - `exportTo()` - Eksportuje pomiary do pliku

### 2.4 Operacje na Plikach

#### 2.4.1 CSVStorage
**Plik**: `CSVStorage.java`
- Implementuje MeasurementStorage dla plików CSV
- Format pliku: data;ciśnienie_górne;ciśnienie_dolne;puls
- **Obsługa plików**:
  - Automatyczne tworzenie katalogów
  - Walidacja danych podczas odczytu
  - Obsługa błędów I/O

#### 2.4.2 CSVExporter
**Plik**: `CSVExporter.java`
- Implementuje FileExporter dla formatu CSV
- Używa średnika jako separatora
- Formatuje datę w formacie "dd.MM.yyyy HH:mm:ss"

#### 2.4.3 TXTExporter
**Plik**: `TXTExporter.java`
- Implementuje FileExporter dla formatu TXT
- Generuje czytelny raport tekstowy
- Dodaje nagłówki i formatowanie

### 2.5 Interfejs Użytkownika

#### 2.5.1 AbstractFrame
**Plik**: `AbstractFrame.java`
- Bazowa klasa dla wszystkich okien
- Definiuje wspólne funkcjonalności:
  - Tworzenie przycisków
  - Obsługa błędów
  - Zarządzanie layoutem

#### 2.5.2 MainFrame
**Plik**: `MainFrame.java`
- Główne okno aplikacji
- **Funkcjonalności**:
  - Panel nawigacyjny
  - Dynamiczne przełączanie widoków
  - Spójny styl interfejsu

#### 2.5.3 AddMeasurementFrame
**Plik**: `AddMeasurementFrame.java`
- Formularz dodawania nowego pomiaru
- Walidacja wprowadzanych danych
- Automatyczne przechodzenie między polami

#### 2.5.4 ViewMeasurementsFrame
**Plik**: `ViewMeasurementsFrame.java`
- Wyświetla tabelę pomiarów
- Umożliwia sortowanie
- Oblicza średnie wartości

#### 2.5.5 SaveOptionsFrame
**Plik**: `SaveOptionsFrame.java`
- Opcje eksportu danych
- Obsługa wyboru pliku
- Obsługa różnych formatów zapisu

### 2.6 System Wyjątków

#### 2.6.1 MeasurementException
**Plik**: `MeasurementException.java`
- Bazowy wyjątek dla błędów związanych z pomiarami

#### 2.6.2 ValidationException
**Plik**: `ValidationException.java`
- Wyjątek dla błędów walidacji
- Przechowuje informacje o nieprawidłowych polach

#### 2.6.3 FileOperationException
**Plik**: `FileOperationException.java`
- Wyjątek dla błędów operacji na plikach
- Zawiera informacje o typie operacji i ścieżce pliku

#### 2.6.4 EmptyFieldException
**Plik**: `EmptyFieldException.java`
- Wyjątek dla pustych pól formularza

## 3. Format Pliku CSV
- Separator: średnik (;)
- Kolumny:
  1. Data i czas (dd.MM.yyyy HH:mm:ss)
  2. Ciśnienie górne (liczba całkowita)
  3. Ciśnienie dolne (liczba całkowita)
  4. Puls (liczba całkowita)

## 4. Wzorce Projektowe
1. Builder (MeasurementBuilder)
2. Factory (FrameFactory)
3. Singleton (ViewMeasurementsFrame)
4. Strategy (FileExporter)
5. Template Method (AbstractFrame)

## 5. Autorzy

- Mateusz Bogacz-Drewniak ([GitHub](https://github.com/mateusz-bogacz-collegiumwitelona))
- Mateusz Chimkowski ([GitHub](https://github.com/Chimek006))
- Michał Żmuda ([GitHub](https://github.com/Sigurvegarinn2000))

*Collegium Witelona Uczelnia Państwowa, 2025*