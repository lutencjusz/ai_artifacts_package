---
name: playwright-form-testing
description: >
  Konfiguruje i uruchamia testy GUI Playwright dla formularzy HTML w projekcie Java + Maven.
  Używaj tego skilla, gdy użytkownik chce testować formularz webowy w Playwright dla Javy,
  pisać zautomatyzowane testy przeglądarkowe walidacji formularza (wymagane pola, format, zgodność),
  skonfigurować Playwright CLI z Mavenem, wygenerować uporządkowany raport błędów gdy testy przechodzą lub nie przechodzą oraz zweryfikować
  scenariusze happy-path / negative-path wysyłania formularza — nawet jeśli użytkownik nie mówi
  wprost "Playwright". Słowa wyzwalające: test formularza, test GUI, automatyzacja przeglądarki,
  Playwright Java, test Maven, walidacja formularza, wymagane pola, testuj formularz, testy GUI,
  automatyzacja testów, testy przeglądarki, alternatywa dla Selenium, test przeglądarkowy JUnit.
---

# Testowanie formularzy Playwright — Java + Maven

## ⚡ Niezmienniki (przeczytaj najpierw)

Zanim napiszesz choć jedną linijkę kodu testów, wykonaj te pięć kroków:

1. **Potwierdź, że URL formularza lub ścieżka pliku** istnieje i jest dostępna.
2. **Zadaj użytkownikowi pytania doprecyzowujące** (poniżej) — zakres decyduje o tym, jakie testy powstaną.
3. **Uruchom `mvn exec:java ... install <browser>`** co najmniej raz, aby pobrać binaria przeglądarki.
4. **Utwórz/uzupełnij `Bledy_YYYY-MM-DD_HH-mm.md`** po każdym uruchomieniu testów, nawet jeśli wszystkie testy przejdą lub nie przejdą.
5. **Dokumentuj wyniki przy każdym uruchomieniu wszystkich testów** — to klucz do zrozumienia regresji i postępu.

---

## Pytania doprecyzowujące (zadaj przed pisaniem testów)

| # | Pytanie | Dlaczego to ważne |
|---|----------|----------------|
| 1 | Co robi formularz? (rejestracja / logowanie / kontakt) | Określa zestaw pól |
| 2 | Które pola są wymagane? | Definiuje asercje dla TC-01 |
| 3 | Czy są walidacje formatu? (e-mail, telefon, min. długość) | Dodaje TC-03 / TC-04 |
| 4 | Czy jest pole potwierdzenia hasła? | Dodaje test niezgodności TC-05 |
| 5 | Czy testujemy ścieżkę pozytywną (happy path)? | Dodaje asercję sukcesu TC-06 |
| 6 | Jaka przeglądarka? (domyślnie Firefox / Chromium / WebKit) | Wybór `playwright.firefox()` itp. |
| 7 | Headless czy widoczna? | `setHeadless(false)` do demo, `true` do CI |

---

## Checklista konfiguracji

```xml
<!-- Zależności pom.xml -->
<dependency>
  <groupId>com.microsoft.playwright</groupId>
  <artifactId>playwright</artifactId>
  <version>1.51.0</version>   <!-- zaktualizuj do najnowszej stabilnej -->
</dependency>
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.11.4</version>
  <scope>test</scope>
</dependency>
```

```bash
# Jednorazowa instalacja przeglądarki (tu Firefox; zamień na chromium / webkit)
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install firefox"

# Uruchom wszystkie testy
mvn test
```

---

## Szkielet klasy testowej

```java
@BeforeAll
static void launchBrowser() {
    playwright = Playwright.create();
    browser    = playwright.firefox().launch(
        new BrowserType.LaunchOptions().setHeadless(false)
    );
}

@BeforeEach
void newPage() {
    context = browser.newContext(new Browser.NewContextOptions()
            .setJavaScriptEnabled(true));
    page = context.newPage();
    page.navigate(PAGE_URL);
    // Poczekaj na pełne załadowanie strony i wykonanie skryptów
    page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    // Jawnie wyczyść wszystkie pola – ochrona przed autouzupełnianiem przeglądarki
    page.evaluate("() => { ['firstName','lastName','email','password','confirmPassword']" +
            ".forEach(id => { var el = document.getElementById(id); if (el) el.value = ''; }); }");
}
```

Używaj selektorów `#fieldId` albo ról ARIA. Mapuj komunikaty błędów do identyfikatorów `#fieldId-error`.

---

## Zalecane identyfikatory testowe

| Pole wejściowe      | Selektor         | Selektor błędu        |
|-----------------|------------------|-----------------------|
| Imię                | `#firstName`     | `#firstName-error`    |
| Nazwisko            | `#lastName`      | `#lastName-error`     |
| E-mail              | `#email`         | `#email-error`        |
| Hasło               | `#password`      | `#password-error`     |
| Potwierdź hasło     | `#confirmPassword` | `#confirmPassword-error` |
| Przycisk wyślij     | `#submit-btn`    | —                     |
| Komunikat sukcesu   | `#success-msg`   | —                     |

---

## Minimalny zestaw przypadków testowych

| ID    | Scenariusz                | Kluczowa asercja                                   |
|-------|---------------------------|----------------------------------------------------|
| TC-01 | Wysłanie pustego formularza | Widoczne wszystkie wymagane błędy przez `isVisible()` |
| TC-02 | Jedno wymagane pole puste | Widoczny tylko błąd tego pola                      |
| TC-03 | Niepoprawny format e-mail | `#email-error` widoczne, tekst zawiera "prawidłowy" |
| TC-04 | Hasło < minimalna długość | `#password-error` widoczne, tekst zawiera "8"      |
| TC-05 | Niezgodność haseł         | `#confirmPassword-error` widoczne                  |
| TC-06 | Wszystkie dane poprawne   | `#success-msg` widoczne, formularz ukryty          |

---

## Zintegrowany przykład — formularz rejestracji, Firefox, zakres pól wymaganych

**Scenariusz:** Użytkownik chce testować formularz rejestracji w `index.html` pod kątem pól wymaganych i ścieżki pozytywnej w Firefox.

```java
// TC-01: puste wysłanie → pięć błędów
@Test void emptyFormShowsAllErrors() {
    page.click("#submit-btn");
    assertAll(
        () -> assertTrue(page.isVisible("#firstName-error")),
        () -> assertTrue(page.isVisible("#email-error")),
        () -> assertTrue(page.isVisible("#password-error"))
    );
}

// TC-06: poprawne dane → baner sukcesu, formularz znika
@Test void validDataShowsSuccess() {
    page.fill("#firstName",       "Jan");
    page.fill("#lastName",        "Kowalski");
    page.fill("#email",           "jan@example.com");
    page.fill("#password",        "Haslo123!");
    page.fill("#confirmPassword", "Haslo123!");
    page.click("#submit-btn");
    assertTrue(page.isVisible("#success-msg"));
    assertFalse(page.isVisible("#register-form"));
}
```

**Pozytywny:** poprawne dane → baner sukcesu widoczny, formularz ukryty.  
**Negatywny:** puste wysłanie → wszystkie pola błędów widoczne, formularz nieukryty.

---

## Częste pułapki

| Problem | Rozwiązanie |
|---------|-----|
| `Executable doesn't exist` | Ponownie uruchom `install firefox` |
| Element błędu istnieje, ale nie jest wykrywany | Użyj `isVisible()`, nie tylko sprawdzania obecności w DOM |
| Test headless nie przechodzi w CI | Ustaw `setHeadless(true)` |
| Uszkodzona ścieżka `file:///` na Windows | Użyj `.replace("\\", "/")` |
| Ostrzeżenie Surefire o forked-stream | Kosmetyczne — zignoruj |

---

## Artefakty wyjściowe

```
katalog-projektu/
├── index.html                         # testowany formularz
├── pom.xml                            # zależności Playwright + JUnit 5
├── src/test/java/.../FormTest.java    # klasa testowa JUnit 5
├── target/surefire-reports/           # raporty testów XML + txt
└── Bledy_YYYY-MM-DD_HH-mm.md          # raport błędów (generuj zawsze)
```

---

## Mini benchmark / release-gate

Po każdej zmianie skilla wykonaj krótki odczyt jakości:

```md
| Scenariusz | Without skill | With skill | Delta |
|-----------|----------------|------------|-------|
| TC core (TC-01..TC-06) | ... | ... | ... |
```

Warunki `GO` (na bazie `skill-optimizer`):

- Brak uniwersalnego 0% (brak kryterium, które zawsze pada przy włączonym skillu)
- Brak regresji na krytycznych scenariuszach (ujemny delta)
- Zapisany przebieg z datą i wynikiem (`Bledy_YYYY-MM-DD_HH-mm.md`)
- Niezamknięte regresje mają dopisane działania naprawcze

Jeśli którykolwiek warunek nie jest spełniony: `NO-GO` i poprawki przed kolejną iteracją.

---

## Checklista bramki wydania

Przed oznaczeniem tego skilla jako „zrobiony” dla sesji:

- [ ] `mvn test` przechodzi lub porażki są opisane w `Bledy_YYYY-MM-DD_HH-mm.md`
- [ ] `Bledy_YYYY-MM-DD_HH-mm.md` utworzony/zaktualizowany z tabelą wyników i datą uruchomienia
- [ ] Brak uniwersalnego 0% przy włączonym skillu
- [ ] Brak regresji na scenariuszach krytycznych (ujemny delta)
- [ ] Zapisany mini benchmark `without-skill` vs `with-skill`
- [ ] Zainstalowane binaria przeglądarki (`install firefox/chromium/webkit`)

---

## Referencje

- `references/bledy-template.md` — szablon do skopiowania dla `Bledy_YYYY-MM-DD_HH-mm.md`
- Playwright Java docs: https://playwright.dev/java/docs/intro
- JUnit 5 docs: https://junit.org/junit5/docs/current/user-guide/
