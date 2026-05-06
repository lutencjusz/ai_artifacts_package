/re---
name: forecast-algorithm
description: "Provides a structured operational checklist to diagnose why a forecast document was not created for a given storeName and targetMonth, based on the logic in ForecastService.java. Use when a user needs to understand the specific conditions that prevented a forecast from being generated, or when they want to quickly identify which part of the algorithm's decision process is responsible for the outcome. Trigger terms include: forecast algorithm, forecast diagnosis, missing forecast, forecast conditions, forecast logic."
metadata:
  tags: forecast, diagnosis, algorithm, conditions, logic
---
## When to use
Use this skill when you need to diagnose why a forecast document was not created for a specific `storeName` and `targetMonth`. This skill provides a structured checklist to identify which condition in the `generateForecasts(...)` method of `ForecastService.java` prevented the forecast from being generated. Trigger this skill when users report missing forecasts or want to understand the underlying logic of the forecast generation process.

Źródło prawdy:
- `src/main/java/pl/invoicemanager/service/ForecastService.java`

## Cel
Na podstawie `storeName` ustalić, **który warunek w `generateForecasts(...)` zablokował utworzenie dokumentu prognozy**.

## Wejście

Minimum:
- `storeName`
- `targetMonth` (`yyyy-MM`, zwykle bieżący miesiąc)

Pomocniczo:
- `bankAccount`
- pozycje z `document_items`
- `saleDate`
- `category`

---

## Checklist operacyjny

- [ ] Czy `targetMonth` to bieżący miesiąc?
- [ ] Czy nazwa ma dokumenty w `M-1` i `M-2`?
- [ ] Czy dokumenty historyczne nie są: `forecasted=true`, `INCOME`, `transport`, `storeName=null`?
- [ ] Czy po normalizacji mają ten sam `bankAccount`?
- [ ] Czy dokumenty z `M-1` i `M-2` mają wspólny rdzeń pozycji?
- [ ] Jeśli nie mają rdzenia: czy mają wspólny `itemsKey`?
- [ ] Czy w `targetMonth` nie istnieje już realny dokument z tym samym kontem i pasującym wzorcem pozycji?
- [ ] Czy `expectedDay` nie wypadł przed dzisiejszą datą?

---

## Jak działa decyzja algorytmu

### 1. Miesiąc docelowy

Prognoza działa tylko dla **bieżącego miesiąca**.

Jeśli `targetMonth != YearMonth.now()`, wynik będzie pusty.

### 2. Historia wejściowa

Brane są tylko dokumenty z:
- `M-1`
- `M-2`

Starsza historia nie wpływa na wynik.

### 3. Filtr wstępny

Do analizy wchodzą tylko dokumenty:
- nie-forecast (`forecasted != true`)
- z nazwą (`storeName != null`)
- typu `EXPENSE`
- z kategorią inną niż `transport`

### 4. Grupowanie

Podstawowy klucz:

```text
normalize(storeName) | normalize(bankAccount)
```

Wniosek praktyczny:
- ta sama nazwa + inne konto = różne grupy

### 5. Dopasowanie pozycji

Algorytm najpierw szuka **wspólnego rdzenia pozycji** między `M-1` i `M-2`.

Jeśli rdzeń istnieje, dokument pasuje do wzorca, gdy zawiera ten rdzeń.
To pozwala przejść przypadkom z dodatkową pozycją typu `opłata jednorazowa`.

Jeśli rdzenia nie ma, używany jest fallback:

```text
strict itemsKey
```

Jeśli nie ma ani rdzenia, ani wspólnego `itemsKey`, prognoza nie powstanie.

### 6. Blokada przez realny dokument

Jeśli w `targetMonth` istnieje już realny dokument z:
- tą samą nazwą,
- tym samym kontem,
- tym samym wzorcem pozycji,

to prognoza nie zostaje zapisana.

### 7. Blokada przez datę

Algorytm liczy:

```text
expectedDay = round(avg(dayOfMonth(saleDate)))
```

Jeśli ten dzień już minął w bieżącym miesiącu, prognoza nie powstanie.

---

## Szybkie mapowanie: objaw -> przyczyna

| Objaw | Najbardziej prawdopodobna przyczyna |
|---|---|
| Nazwa jest w historii, ale nie ma jej w prognozach | Brak dokumentu w `M-1` lub `M-2`, albo odpadła na filtrze |
| Ta sama nazwa w obu miesiącach, ale nadal brak prognozy | Różne `bankAccount` po normalizacji |
| Ta sama nazwa i konto, jedna faktura ma dodatkową pozycję | Po nowej logice to samo w sobie nie powinno blokować prognozy |
| Ta sama nazwa i konto, ale pozycje są całkiem inne | Brak wspólnego rdzenia i brak wspólnego `itemsKey` |
| Historia jest zgodna, ale nadal brak prognozy | Istnieje już realny dokument w `targetMonth` |
| Brak prognozy pod koniec miesiąca | `expectedDay` już minął |

---

## Szablon odpowiedzi dla użytkownika

```text
Prognoza nie utworzyła dokumentu dla "<NAZWA>", ponieważ <KONKRETNY_WARUNEK>.
Dowód: w <M-2> było <...>, w <M-1> było <...>, a algorytm wymaga <...>.
```

---

## Uwaga

`replaceForecastIfRealExists(...)` usuwa prognozę po znormalizowanej nazwie w tym samym miesiącu. To osobna, uproszczona logika względem samego generowania prognoz.

