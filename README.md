# Booker - System Wypożyczalni Książek

Webowa aplikacja do zarządzania wypożyczalnią książek, oparta na architekturze korporacyjnej **Jakarta EE** (JSF + PrimeFaces, EJB, JPA/Hibernate) oraz serwerze aplikacji **WildFly**.

---

## Demo / Prezentacja działania

---

## O projekcie

Projekt został wykonany w ramach zajęć „Języki programowania dynamicznych stron internetowych II” w semestrze zimowym 2023/2024, studiów pierwszego stopnia.

**Booker** to trójwarstwowa aplikacja zrealizowana w architekturze korporacyjnej jako projekt EAR (Enterprise Archive), składający się z modułu biznesowego EJB oraz modułu webowego WAR. System umożliwia przeglądanie katalogu książek z zachowaniem stanu widoku, dynamiczne zarządzanie koszykiem wypożyczeń, automatyczną kalkulację kosztów najmu oraz administrację zamówieniami, zwrotami i opłatami za opóźnienia.

Interfejs użytkownika oparto na standardzie **Jakarta Faces** (JSF) z biblioteką komponentów **PrimeFaces**, zunifikowanym systemie szablonów Facelets oraz architekturze responsywnego ciemnego motywu. Warstwa logiki biznesowej została oddelegowana do bezstanowych komponentów EJB (Stateless Session Beans), natomiast persystencja danych jest zarządzana przez JPA (Hibernate).

---

## Kluczowe funkcjonalności

- **Katalog pozycji:** Przeglądanie oferty z wykorzystaniem leniwego ładowania (`LazyBookDataModel`), stronicowania oraz mechanizmu zapamiętywania aktywnej podstrony (`saveState`).
- **Koszyk wypożyczeń:** Dynamiczne dodawanie książek, natychmiastowe usuwanie pozycji, konfigurowalny czas wypożyczenia (1–90 dni) z automatycznym przeliczaniem kosztu łącznego.
- **Zaawansowany system kontroli dostępu (RBAC):**
  - `Gość / Użytkownik początkowy`: dostęp do strony głównej, podglądu katalogu książek oraz modułu logowania/rejestracji.
  - `User`: przeglądanie szczegółów książek, dodawanie/usuwanie książek z koszyka oraz zamawianie koszyka.
  - `Worker`: weryfikacja płatności, zatwierdzanie wydań, przyjmowanie zwrotów oraz automatyczne naliczanie opłat karnych za nieterminowy zwrot.
  - `Admin`: zarządzanie kontami użytkowników, modyfikacja haseł, przydzielanie wielu ról równocześnie oraz bezpieczne usuwanie kont powiązanych relacjami.
- **Unifikacja UI i okna modalne:** Globalny szablon bazowy Facelets z układem *sticky footer*, dynamicznym menu nawigacyjnym oraz oknem dialogowym (`p:dialog`) prezentującym podziękowania i źródła materiałów demonstracyjnych.

---

## Zrzuty ekranu

---

### Model bazy danych

Projekt wykorzystuje relacyjną strukturę danych zapewniającą pełną integralność oraz śledzenie historii modyfikacji rekordów:

---

## Technologie i narzędzia

- **Język:** Java 17+ (Jakarta EE 10)
- **Warstwa prezentacji:** JSF (Jakarta Server Faces), PrimeFaces 13 (przestrzeń nazw `jakarta.*`)
- **Warstwa biznesowa:** EJB 3.x (Enterprise JavaBeans – `@Stateless`, `@EJB`)
- **Warstwa danych:** JPA 3.0 (Hibernate 6), MySQL / MariaDB (np. w środowisku XAMPP)
- **Serwer aplikacji:** WildFly 28+
- **Struktura wdrożeniowa:** EAR (`booker_EAR` zawierający moduły `booker_ejb` oraz `booker_web`)

---

## Instrukcja instalacji i uruchomienia

### Wymagania

- **Java JDK:** wersja 17 lub 21
- **Serwer aplikacji:** WildFly 28+ (obsługujący specyfikację Jakarta EE 10)
- **Baza danych:** MySQL lub MariaDB (np. z pakietu XAMPP)
- **Sterownik JDBC:** MySQL Connector/J zainstalowany jako moduł w serwerze WildFly
- **IDE:** Eclipse IDE for Enterprise Java and Web Developers

### Instrukcja

1. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/AdiKungen/Projekt_JPDSI2_Budny_Adrian_PAW3.git
   ```

2. **Przygotowanie bazy danych:**
   * Uruchom moduł MySQL w panelu kontrolnym XAMPP.
   * Utwórz nową bazę danych o nazwie `booker`.
   * Zaimportuj schemat tabel oraz przykładowe dane (plik `booker.sql` zawierający strukturę tabel `users`, `role`, `permission`, `genre`, `book`, `rental`, `rentalitem` oraz fikcyjne pozycje katalogowe).

3. **Konfiguracja źródła danych w WildFly (Datasource):**
   * Upewnij się, że sterownik MySQL Connector/J jest zarejestrowany w katalogu modułów serwera WildFly.
   * W pliku konfiguracyjnym serwera (`standalone/configuration/standalone.xml`) dodaj źródło danych JNDI zgodne z konfiguracją zdefiniowaną w `persistence.xml`:
   
     ```xml
     <datasource jndi-name="java:/jsfcourse/bookerDS" pool-name="bookerDS">
         <connection-url>jdbc:mysql://localhost:3306/booker?useSSL=false&amp;serverTimezone=UTC&amp;allowPublicKeyRetrieval=true</connection-url>
         <driver>mysql</driver>
         <security>
             <user-name>root</user-name>
             <password></password>
         </security>
     </datasource>
     ```

4. **Import projektu do środowiska IDE:**
   * W Eclipse wybierz `File` -> `Import...` -> `Existing Projects into Workspace`.
   * Zaimportuj katalogi projektów: `booker_EAR`, `booker_ejb`, `booker_web`.
   * W widoku `Servers` dodaj serwer WildFly, wskazując ścieżkę do jego katalogu instalacyjnego.

5. **Wdrożenie i uruchomienie:**
   * Kliknij prawym przyciskiem myszy na serwer WildFly w zakładce serwerów, wybierz `Add and Remove...` i dodaj komponent `booker_EAR`.
   * Uruchom serwer (`Start` / `Debug`).
   * Otwórz przeglądarkę i przejdź pod adres:
     
     ```text
     http://localhost:8080/booker/
     ```

---

## Podziękowania / Credits

- **Komponenty interfejsu UI:** biblioteka [PrimeFaces](https://www.primefaces.org/) udostępniona na licencji [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
- **Serwer aplikacji:** środowisko [WildFly](https://www.wildfly.org/) udostępnione na licencji [LGPL v2.1](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html).
- **Zestaw ikon interfejsu:** ikony [PrimeIcons](https://github.com/primefaces/primeicons) autorstwa zespołu PrimeFaces, udostępnione na licencji [MIT](https://opensource.org/licenses/MIT).
- **Okładki książek (Materiały demonstracyjne):** grafika oraz zdjęcia pobrane z serwisu [Unsplash](https://unsplash.com/) i wykorzystane zgodnie z [Unsplash License](https://unsplash.com/license):
  - _Cienie Zapomnianych Gór_: zdjęcie autorstwa [Jeremy Bishop](https://unsplash.com/@jeremybishop) z serwisu [Unsplash](https://unsplash.com).
  - _Milczenie Północy_: zdjęcie autorstwa [Jocelyn Wong](https://unsplash.com/@jocelynwong) z serwisu [Unsplash](https://unsplash.com).
  - _Stacja Orbitalna Orion_: zdjęcie autorstwa [Jack Dong](https://unsplash.com/@lwdzl) z serwisu [Unsplash](https://unsplash.com).
  - _Jesień w Dolinie Róż_: zdjęcie autorstwa [Amir Jalili](https://unsplash.com/@amirjaalili) z serwisu [Unsplash](https://unsplash.com).
  - _Krypta Dawnych Mistrzów_: zdjęcie autorstwa [Greg Willson](https://unsplash.com/@gregwillson) z serwisu [Unsplash](https://unsplash.com).
  - _Kroniki Zaginionego Miasta_: zdjęcie autorstwa [Joshua Kettle](https://unsplash.com/@joshuakettle) z serwisu [Unsplash](https://unsplash.com).
  - _Przełęcz Ciemności_: zdjęcie autorstwa [Nik Iurev](https://unsplash.com/@lastoops) z serwisu [Unsplash](https://unsplash.com).
  - _Kod Nowego Świata_: grafika autorstwa [Cash Macanaya](https://unsplash.com/@cashmacanaya) z serwisu [Unsplash](https://unsplash.com).

---

## Zastrzeżenie / Disclaimer

**PL:**   
Serwis został stworzony wyłącznie w celach edukacyjnych i prezentacji portfolio. Nie prowadzi działalności komercyjnej i nie jest w żaden sposób powiązany, sponsorowany ani zrzeszony z żadnymi rzeczywistymi podmiotami, serwisami internetowymi czy organizacjami posługującymi się nazwą „Booker” lub jakąkolwiek jej odmianą.   
Wszelkie tytuły książek, nazwiska autorów, opisy itp. stanowią fikcyjne dane testowe (dane pozorowane) i nie mają na celu reprezentowania ani nawiązywania do rzeczywistych publikacji bądź osób. Wszelkie podobieństwo jest całkowicie przypadkowe.

**EN:**   
This website was built solely for educational and portfolio demonstration purposes. It does not provide commercial services and is not affiliated with, sponsored by, or connected to any real-world entities, websites, or organizations using the name "Booker" or any variation thereof.   
All book titles, author names, descriptions, etc. are fictional test data (mock data) and are not intended to represent or reference real-world publications or individuals. Any resemblance is entirely coincidental.

---

## Licencja / License

**PL:**  
Copyright (c) 2026 Adrian Budny. Wszelkie prawa zastrzeżone.  
Kod źródłowy tego projektu udostępniony jest wyłącznie do wglądu w celach demonstracji portfolio i weryfikacji umiejętności. Kopiowanie, modyfikowanie, rozpowszechnianie lub wykorzystywanie tego kodu w celach komercyjnych lub prywatnych bez pisemnej zgody autora jest zabronione.

**EN:**  
Copyright (c) 2026 Adrian Budny. All rights reserved.  
This source code is made publicly available solely for portfolio demonstration and technical evaluation. No permission is granted to copy, modify, distribute, or use this code for any commercial or non-commercial purpose without prior written consent from the author.
