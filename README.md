# SelenideAi - testy formularza

Projekt zawiera lokalny formularz rejestracji (`index.html`) oraz zestaw testow GUI w Selenide (JUnit 5 + Maven).

## Zakres testow

- `TC-01`: wyslanie pustego formularza -> wszystkie bledy widoczne
- `TC-02`: jedno pole wymagane puste -> tylko jeden blad
- `TC-03`: niepoprawny e-mail -> blad e-mail widoczny
- `TC-04`: haslo krotsze niz 8 znakow -> blad dlugosci hasla
- `TC-05`: niezgodne hasla -> blad potwierdzenia hasla
- `TC-06`: poprawne dane -> sukces i ukrycie formularza

## Szybki start

```bash
mvn test
```

## Publikacja katalogu .github do GitHub Packages

- Workflow: `.github/workflows/publish-github-dir-package.yml`
- Publikacja tworzy artefakt `zip` z katalogiem `.github` jako classifier `github-dir`.
- Uruchom workflow recznie z zakladki `Actions` (opcjonalnie podaj `package_version`).

Po `BUILD SUCCESS` pakiet bedzie widoczny w sekcji `Packages` repozytorium.

## Uwagi

- Testy otwieraja lokalny plik `index.html` przez URI `file:///...`.
- Domyslnie testy uruchamiaja Firefox w trybie headless (`Configuration.headless=true`).

