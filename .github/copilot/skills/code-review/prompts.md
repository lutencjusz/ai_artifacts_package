# Gotowe polecenia – skill code-review

Kopiuj i wklejaj do czatu GitHub Copilot (CLI lub IDE).

---

## Szybki przegląd pliku

```
Użyj skilla `code-review` i przejrzyj plik <ŚCIEŻKA_DO_PLIKU>
```

## Przegląd ostatniego commita (git diff)

```
Użyj skilla `code-review` na poniższy diff:

<wklej wynik: git diff HEAD~1>
```

## Przegląd z priorytetem bezpieczeństwa

```
Użyj skilla `code-review`, focus=security, level=deep dla pliku <ŚCIEŻKA>
```

## Przegląd konkretnej funkcji

```
Użyj skilla `code-review` dla funkcji `<NAZWA_FUNKCJI>()` w pliku <ŚCIEŻKA>
```

## Przegląd całego PR-a

```
Użyj skilla `code-review` na poniższy diff PR-a. Zwróć pełny raport Markdown.

<wklej: git diff main...feature-branch>
```

## Szybki przegląd (tylko błędy krytyczne)

```
Użyj skilla `code-review`, level=quick. Pokaż tylko błędy krytyczne dla pliku <ŚCIEŻKA>
```

## Przegląd pod kątem wydajności

```
Użyj skilla `code-review`, focus=performance dla <ŚCIEŻKA>
```

---

## Użycie w GitHub Copilot CLI (terminal)

```bash
# Sprawdź zmiany w bieżącej gałęzi
git diff HEAD~1 | gh copilot suggest "Zrób code review tego diff-a zgodnie ze skillem code-review"

# Przejrzyj konkretny plik
cat src/api/auth.js | gh copilot suggest "Wykonaj code review tego kodu"

# Wyjaśnij konkretny fragment
gh copilot explain "Co jest nie tak z tym kodem: $(cat src/utils.js | head -50)"
```


