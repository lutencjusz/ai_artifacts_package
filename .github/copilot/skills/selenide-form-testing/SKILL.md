---
name: selenide-form-testing
description: >
  Konfiguruje i uruchamia testy GUI Selenide WebDriver dla formularzy HTML w projekcie Java + Maven.
  Uzywaj tego skilla, gdy uzytkownik chce testowac formularz webowy z Selenium/Selenide,
  pisac automatyczne testy walidacji formularza (pola wymagane, format, zgodnosc hasel),
  generowac raport `Bledy_YYYY-MM-DD_HH-mm.md` po kazdym uruchomieniu oraz utrzymac
  scenariusze TC-01..TC-06 zgodne miedzy projektami.
  Slowa wyzwalajace: Selenide, Selenium WebDriver, test formularza, JUnit, Maven,
  walidacja formularza, required fields, e2e formularza, browser automation Java.
---

# Testowanie formularzy Selenide - Java + Maven

## Niezmienniki (musza byc zawsze spelnione)

1. Potwierdz, ze formularz (`URL` albo `file:///...`) jest dostepny przed pisaniem testow.
2. Odtworz minimalny zestaw testow `TC-01..TC-06` i zachowaj te same selektory elementow.
3. Po kazdym uruchomieniu testow utworz lub zaktualizuj `Bledy_YYYY-MM-DD_HH-mm.md` w katalogu glownym projektu.
4. Uruchom pelny `mvn test` po zmianach w testach i po zmianach skilla.
5. W raporcie zapisz mini benchmark `without-skill` vs `with-skill` i decyzje `GO/NO-GO`.

## Quick-start scenariusze (gotowe prompty)

## Kiedy uzyc tych promptow

Uzyj promptow QS-01..QS-05, gdy w tresci pojawiaja sie sygnaly:

- domenowe: `Selenide`, `Selenium WebDriver`, `formularz`, `JUnit`, `Maven`, `index.html`
- failure-cues: `nie przechodzi`, `flaky`, `timeout`, `walidacja`, `required fields`
- shape-cues: `TC-01..TC-06`, `Bledy_YYYY-MM-DD_HH-mm.md`, `without-skill vs with-skill`

### QS-01 - Core TC-01..TC-06

**Cel:** pelny zestaw testow formularza i raport uruchomienia.

```text
Utworz testy Selenide + JUnit 5 dla formularza w index.html.
Wymagane przypadki: TC-01..TC-06 (puste pola, pojedyncze pole puste, bledny email,
za krotkie haslo, niezgodne hasla, poprawne wyslanie).
Zachowaj selektory #firstName, #lastName, #email, #password, #confirmPassword,
#submit-btn, #success-msg. Uruchom mvn test i uzupelnij Bledy_YYYY-MM-DD_HH-mm.md.
```

**Must include:** `pom.xml`, testy JUnit, wynik `mvn test`, raport bledow.

### QS-02 - Omission-prone checklist

**Cel:** wymusic elementy, ktore najczesciej sa pomijane.

```text
Przygotuj testy formularza w Selenide i nie pomijaj:
1) wszystkich 6 przypadkow TC-01..TC-06,
2) asercji widocznosci komunikatow bledow,
3) asercji sukcesu i ukrycia formularza,
4) raportu Bledy_YYYY-MM-DD_HH-mm.md po uruchomieniu,
5) tabeli mini benchmarku with-skill vs without-skill.
```

**Must include:** checklista outputu i decyzja `GO/NO-GO`.

### QS-03 - Noisy context resilience

**Cel:** utrzymac poprawny wynik mimo dodatkowych, mylacych wymagan.

```text
Mam projekt Java z formularzem i duzo szumu w wymaganiach.
Pomin wszystko, co nie dotyczy testow formularza i skup sie na Selenide.
Zrob tylko to, co krytyczne: konfiguracja Maven, testy TC-01..TC-06,
uruchomienie mvn test, raport Bledy_YYYY-MM-DD_HH-mm.md.
Nie przechodz na Playwright ani Cypress.
```

**Must include:** filtracja szumu i trzymanie zakresu Selenide-only.

### QS-04 - Migracja z Playwright

**Cel:** mapowanie API Playwright -> Selenide bez zmiany logiki testow.

```text
Przepisz testy formularza z Playwright Java do Selenide.
Zachowaj te same scenariusze i selektory.
Zmapuj operacje fill/click/isVisible/text na odpowiedniki Selenide,
a na koniec uruchom mvn test i wypelnij raport bledow.
```

**Must include:** tabela mapowania API i porownanie pokrycia przypadkow.

### QS-05 - Triaging regresji

**Cel:** szybka diagnoza po nieudanym uruchomieniu.

```text
Testy formularza Selenide nie przechodza.
Zidentyfikuj blad krytyczny, podaj reprodukcje, zaproponuj poprawke,
ponownie uruchom mvn test i zaktualizuj Bledy_YYYY-MM-DD_HH-mm.md.
Dodaj status GO/NO-GO na podstawie release-gates.
```

**Must include:** root-cause, patch, re-run, decyzja bramki.

## Pytania doprecyzowujace

| # | Pytanie | Dlaczego |
|---|---------|----------|
| 1 | Jakie pola formularza sa wymagane? | Definicja TC-01 i TC-02 |
| 2 | Jakie walidacje formatu obowiazuja? | Definicja TC-03 i TC-04 |
| 3 | Czy jest potwierdzenie hasla? | Definicja TC-05 |
| 4 | Co oznacza sukces formularza? | Definicja TC-06 |
| 5 | Jaka przegladarka i tryb (headless/headed)? | Ustawienia Selenide |

## Checklista konfiguracji Maven

```xml
<dependency>
  <groupId>com.codeborne</groupId>
  <artifactId>selenide</artifactId>
  <version>7.5.1</version>
</dependency>
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.11.4</version>
  <scope>test</scope>
</dependency>
```

```bash
mvn test
```

## Mapa selektorow (rekomendowana)

| Pole | Input | Blad |
|------|-------|------|
| Imie | `#firstName` | `#firstName-error` |
| Nazwisko | `#lastName` | `#lastName-error` |
| Email | `#email` | `#email-error` |
| Haslo | `#password` | `#password-error` |
| Potwierdzenie hasla | `#confirmPassword` | `#confirmPassword-error` |
| Submit | `#submit-btn` | - |
| Sukces | `#success-msg` | - |

## Minimalny zestaw przypadkow testowych

| ID | Scenariusz | Asercja |
|----|------------|---------|
| TC-01 | Pusty formularz | wszystkie bledy widoczne |
| TC-02 | Jedno wymagane pole puste | widoczny tylko blad tego pola |
| TC-03 | Bledny format email | `#email-error` widoczny |
| TC-04 | Za krotkie haslo | `#password-error` widoczny i zawiera `8` |
| TC-05 | Niezgodne hasla | `#confirmPassword-error` widoczny |
| TC-06 | Dane poprawne | `#success-msg` widoczny i formularz ukryty |

## Zintegrowany przyklad (Selenide)

```java
@Test
void tc06_validDataShowsSuccessAndHidesForm() {
    $("#firstName").setValue("Jan");
    $("#lastName").setValue("Kowalski");
    $("#email").setValue("jan@example.com");
    $("#password").setValue("Haslo123!");
    $("#confirmPassword").setValue("Haslo123!");
    $("#submit-btn").click();

    $("#success-msg").shouldBe(visible);
    $("#register-form").shouldBe(hidden);
}
```

## Artefakty wyjsciowe

```
katalog-projektu/
|- index.html
|- pom.xml
|- src/test/java/.../FormValidationSelenideTest.java
|- target/surefire-reports/
`- Bledy_YYYY-MM-DD_HH-mm.md
```

## Mini benchmark / release-gate

Po kazdej zmianie skilla uzupelnij:

```md
| Scenariusz | Without skill | With skill | Delta |
|-----------|----------------|------------|-------|
| TC core (TC-01..TC-06) | ... | ... | ... |
| Omission-prone (QS-02) | ... | ... | ... |
| Noisy context (QS-03) | ... | ... | ... |
```

Warunki GO (na bazie `skill-optimizer`):

- brak uniwersalnego 0% przy wlaczonym skillu
- brak regresji (ujemny delta) na scenariuszach krytycznych
- zapisany przebieg benchmarku z data
- otwarte dzialania naprawcze dla nierozwiazanych regresji

W przeciwnym razie: `NO-GO`.

## Referencje

- `references/bledy-template.md`
- Selenide docs: https://selenide.org/documentation.html
- JUnit 5 docs: https://junit.org/junit5/docs/current/user-guide/
- `../skill-optimizer/SKILL.md`
- `../skill-optimizer/rules/activation-design.md`
- `../skill-optimizer/rules/benchmark-loop.md`
- `../skill-optimizer/rules/release-gates.md`

