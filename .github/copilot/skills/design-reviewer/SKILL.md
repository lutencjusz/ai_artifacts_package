---
name: design-review
description: Przegląda zaimplementowany UI i porównuje go z design guide lub opisem stylu. Wyłapuje odchylenia od założonego stylu — złe zaokrąglenia, niezgodne kolory, zbyt ciasne odstępy, nieprawidłową hierarchię tekstu. Używaj po ukończeniu widoku lub gdy coś "nie wygląda jak powinno".
user-invocable: true
argument-hint: "[widok lub komponent do przejrzenia, np. 'Dashboard' albo 'karty statystyk']"
---

Przejrzyj wskazany fragment UI i oceń go względem design guide projektu. Znajdź konkretne odchylenia i zaproponuj poprawki.

## Przygotowanie

1. Znajdź `design-guide.md` w projekcie (zwykle w `docs/`). Przeczytaj go w całości — to Twoja jedyna miara "poprawności".
2. Zidentyfikuj pliki do przejrzenia: widoki, komponenty, CSS/tokens.
3. Jeśli możliwe — zrób screenshot przez Playwright MCP i oceń też wizualnie, nie tylko przez kod.

## Co sprawdzać

### Kolory
- Czy używane kolory pochodzą z design tokens (zmiennych CSS), nie hardkodowanych wartości?
- Czy kolory kategorii są spójne — ta sama kategoria zawsze ma ten sam kolor?
- Czy tekst na kolorowym tle jest ciemnym odcieniem tego koloru (nie szarym)?
- Czy cienie mają właściwy odcień (nie czarny/neutralny szary)?

### Zaokrąglenia
- Czy główne karty używają dużego radius (~24-28px)?
- Czy mniejsze elementy (przyciski, ikony) używają mniejszego (~12-20px)?
- Czy nie ma elementów z ostrymi kątami (radius: 0) tam gdzie nie powinno?

### Typografia i hierarchia
- Czy kwoty finansowe są zawsze pogrubione i największe?
- Czy etykiety/opisy są wyraźnie lżejsze i mniejsze niż liczby?
- Czy tytuly sekcji używają wersalików z letter-spacing?
- Czy czcionka zgadza się z design guide (Plus Jakarta Sans)?

### Przestrzeń (whitespace)
- Czy karty mają hojny wewnętrzny padding (min. 20-24px)?
- Czy sekcje są oddzielone przestrzenią, nie liniami?
- Czy elementy nie są za ciasno upakowane?

### Cienie i głębia
- Czy cienie są subtelne i rozmyte (nie twarde)?
- Czy mają krycie 4-8% (nie więcej)?

### Ikony kategorii
- Czy każda ikona jest w kolorowym kwadracie/prostokącie (nie sama)?
- Czy kwadrat ma właściwy kolor tła kategorii?

### Spójność
- Czy ten sam typ elementu (np. karta statystyki) wygląda identycznie wszędzie?
- Czy nazewnictwo klas/tokenów jest konsekwentne?

## Format raportu

```
## Przegląd: [nazwa widoku]

### ✅ Zgodne z design guide
- [lista tego co wygląda dobrze]

### ❌ Odchylenia do poprawy
- [element]: [opis problemu] → [konkretna poprawka]
- [element]: [opis problemu] → [konkretna poprawka]

### ⚠️ Do dyskusji
- [coś co działa ale może być lepsze]
```

## Wykonanie poprawek

Po raporcie — zapytaj czy naprawiać od razu. Jeśli tak:
- Poprawiaj po jednym problemie naraz
- Weryfikuj każdą zmianę przed przejściem do kolejnej
- Nie zmieniaj rzeczy które nie były w raporcie

Pamiętaj: celem jest zgodność z ustaloną wizją designu, nie "ulepszanie według własnego gustu".
