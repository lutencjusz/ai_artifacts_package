## Code Review – src/api/auth.js

### ✅ Co działa dobrze
- Funkcja `verifyToken()` poprawnie obsługuje wygaśnięcie tokena i zwraca zrozumiałe błędy.
- Struktura pliku jest czytelna, moduły są logicznie podzielone.
- Używasz `async/await` konsekwentnie – brak mieszania z `.then()`.

### ⚠️ Uwagi i sugestie

- **[Bezpieczeństwo]** Linia 42: Sekret JWT jest wczytywany z `process.env.SECRET` bez wartości domyślnej – aplikacja nie zgłosi błędu przy starcie, gdy zmienna nie jest ustawiona.
  ```js
  const secret = process.env.SECRET;
  ```
  ➡️ Sugerowana zmiana:
  ```js
  const secret = process.env.SECRET;
  if (!secret) throw new Error('Brak zmiennej środowiskowej SECRET');
  ```

- **[Czytelność]** Linia 78: Zmienna `d` nie opisuje swojego przeznaczenia.
  ```js
  const d = await db.query(sql);
  ```
  ➡️ Sugerowana zmiana:
  ```js
  const userRecord = await db.query(sql);
  ```

- **[Obsługa błędów]** Linia 95: Pusty blok `catch` – błędy są cichо ignorowane.
  ```js
  } catch (e) {}
  ```
  ➡️ Sugerowana zmiana:
  ```js
  } catch (e) {
    console.error('[auth] Błąd podczas logowania:', e);
    throw e;
  }
  ```

### 🔴 Błędy krytyczne

- **[Bezpieczeństwo]** Linia 110: Hasło użytkownika jest logowane w trybie debug.
  ```js
  console.log('Login attempt:', username, password);
  ```
  ➡️ Nigdy nie loguj haseł. Usuń `password` z logu.

### 📋 Podsumowanie

| Kategoria       | Ocena |
|-----------------|-------|
| Bezpieczeństwo  | 🔴    |
| Czytelność      | 🟡    |
| Wydajność       | 🟢    |
| Testy           | 🟡    |
| Konwencje       | 🟢    |

> **Priorytet:** napraw błąd krytyczny (linia 110) przed mergem. Pozostałe uwagi warto poprawić w tym samym PR.


