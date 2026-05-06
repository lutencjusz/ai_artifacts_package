# Skill: code-review

## Cel

Wykonaj szczegółowe code review wskazanego pliku, fragmentu kodu lub całego diff-a.
Skill działa niezależnie od języka i frameworka – dostosowuje kryteria do kontekstu projektu.

## Wejście

Podaj jedno z poniższych:

- Nazwę lub ścieżkę pliku: `code-review src/utils.js`
- Fragment kodu wklejony bezpośrednio do czatu
- Wynik `git diff` lub `git diff HEAD~1`
- Całe repozytorium (Copilot przejrzy zmiany w bieżącej gałęzi)

Opcjonalne parametry kontekstowe:

| Parametr       | Opis                                                      | Domyślnie          |
|----------------|-----------------------------------------------------------|--------------------|
| `lang`         | Język programowania (`js`, `ts`, `py`, `java`, …)         | auto-detect        |
| `level`        | Głębokość recenzji: `quick` / `standard` / `deep`        | `standard`         |
| `focus`        | Priorytet: `security` / `performance` / `readability`    | wszystkie równo    |
| `checklist`    | Użyj wbudowanej listy kontrolnej (`yes` / `no`)           | `yes`              |

## Oczekiwane wyjście

Zwróć raport w pliku `raport_code_review.md` formacie Markdown z następującymi sekcjami:

```markdown
## Code Review – <nazwa pliku / commit hash>

### ✅ Co działa dobrze
- ...

### ⚠️ Uwagi i sugestie
- **[Kategoria]** Linia X: opis problemu
  ```<lang>
  // problematyczny kod
  ```
  ➡️ Sugerowana zmiana:
  ```<lang>
  // poprawiony kod
  ```

### 🔴 Błędy krytyczne
- **[Bezpieczeństwo / Logika / Wydajność]** Linia X: opis

### 📋 Podsumowanie
| Kategoria       | Ocena |
|-----------------|-------|
| Bezpieczeństwo  | 🟢 / 🟡 / 🔴 |
| Czytelność      | 🟢 / 🟡 / 🔴 |
| Wydajność       | 🟢 / 🟡 / 🔴 |
| Testy           | 🟢 / 🟡 / 🔴 |
| Konwencje       | 🟢 / 🟡 / 🔴 |
```

## Zasady

1. Zawsze wskazuj numer linii (jeśli dostępny) lub cytat kodu, którego dotyczy uwaga.
2. Do każdej uwagi dołącz sugerowaną poprawkę lub wyjaśnienie.
3. Nie powtarzaj fragmentów kodu, które są poprawne – skupiaj się na problemach.
4. Dostosuj kryteria do języka: np. dla JS sprawdzaj async/await, dla Python – typowanie i PEP8.
5. Sekcja „Błędy krytyczne" pojawia się tylko jeśli naprawdę są poważne problemy.
6. Jeśli kod jest wzorowy, napisz krótkie podsumowanie bez sztucznych uwag.
7. Używaj języka polskiego w raporcie, chyba że użytkownik poprosi inaczej.

## Checklista code review (wbudowana)

### Bezpieczeństwo
- [ ] Brak wstrzyknięcia SQL / XSS / CSRF
- [ ] Dane wejściowe walidowane i sanitizowane
- [ ] Sekrety nie są hardkodowane w kodzie
- [ ] Uprawnienia i autoryzacja sprawdzone

### Jakość i czytelność
- [ ] Funkcje robią jedną rzecz (SRP)
- [ ] Nazwy zmiennych i funkcji są opisowe
- [ ] Brak martwego kodu (`TODO`/`FIXME` bez planu)
- [ ] Złożoność cyklomatyczna jest akceptowalna (max ~10 na funkcję)

### Obsługa błędów
- [ ] Wszystkie ścieżki błędów są obsługiwane
- [ ] Logowanie błędów jest spójne
- [ ] Brak cichych catch-bloków (`catch (e) {}`)

### Wydajność
- [ ] Brak N+1 queries w pętlach
- [ ] Kosztowne operacje nie są wywoływane bez potrzeby
- [ ] Memoizacja/cache tam, gdzie ma sens

### Testy
- [ ] Istnieją testy dla logiki biznesowej
- [ ] Edge-case'y są pokryte
- [ ] Testy są niezależne od siebie

### Konwencje projektu
- [ ] Styl spójny z resztą kodu
- [ ] Komentarze i dokumentacja aktualne
- [ ] Zmiany nie łamią publicznego API bez wersjonowania

## Przykładowe polecenia

- `Użyj skilla code-review i przejrzyj plik src/api/auth.js`
- `Użyj code-review na wynik git diff HEAD~1, focus=security`
- `Użyj skilla code-review, level=deep, focus=performance dla funkcji renderTree()`
- `Zrób code-review całego PR-a, zwróć raport po polsku`


