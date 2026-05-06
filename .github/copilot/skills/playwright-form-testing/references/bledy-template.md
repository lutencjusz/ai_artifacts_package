# Szablon raportu błędów – `Bledy_YYYY-MM-DD_HH-mm.md`

Skopiuj ten plik jako `Bledy_YYYY-MM-DD_HH-mm.md` do katalogu głównego projektu po każdym przebiegu testów.

---

# Raport błędów – [Nazwa formularza]

**Data:** YYYY-MM-DD  
**Przeglądarka:** Firefox / Chromium / WebKit (Playwright build vXXXX)  
**Narzędzie:** Playwright X.Y.Z + JUnit 5.A.B + Maven M.N.P  

---

## Wyniki testów

| ID     | Nazwa testu | Status |
|--------|-------------|--------|
| TC-01  | …           | ✅ PASS / ❌ FAIL |

**Łącznie:** N testów, F błędów, S pominięć

---

## Mini benchmark skilla (required)

| Scenariusz | Without skill | With skill | Delta |
|------------|---------------|------------|-------|
| TC core (TC-01..TC-06) | ... | ... | ... |

**Uniwersalne 0% z włączonym skillem:** tak/nie  
**Regresja (ujemny delta) na krytycznych scenariuszach:** tak/nie  
**Decyzja release gate:** GO / NO-GO

---

## Znalezione błędy

### BUG-001 – [Krótki tytuł]

- **Środowisko:** Firefox 135.0
- **TC:** TC-XX
- **Kroki reprodukcji:**
  1. …
  2. …
- **Oczekiwany wynik:** …
- **Rzeczywisty wynik:** …
- **Priorytet:** Wysoki / Średni / Niski

---

## Uwagi techniczne

_Opcjonalne obserwacje dotyczące środowiska, ostrzeżeń kompilatora itp._

