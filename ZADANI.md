# Ranked League System — Zadani a postup

## O projektu

Trenovaci projekt pro pripravu na pohovory. Cil: napsat kompletni Spring Boot REST API
od nuly, bez pomoci AI (pouze konzultace konceptu).

Tech stack: Java 21, Spring Boot, Spring Data JPA, Hibernate, H2 databaze, Lombok.

---

## Aktualni stav: Faze 2 hotova, Faze 3 ~70 %

Posledni review: 2026-04-27 (vecer). Vsechny dosud zname bugy opraveny, ELO logika je
zero-sum (pure `calculateRating`, ratingy zachycene pred kalkulaci), exception handling
je kompletni a sedi HTTP statusy. `createMatch` je `@Transactional`. Zbyva leaderboard
logika a sprava sezon.

### Kde pokracovat zitra

1. **Leaderboard** — zacni implementaci `LeaderboardService.getLeaderboard(Long seasonId)`:
   - Pridat dotaz do `MatchRepository` (napr. `findBySeasonId(Long seasonId)`)
   - Spocitat statistiky per-hrac z listu zapasu — totalMatches, wins, losses, draws
     (vyhodnoceni vysledku stejne jako v `MatchServiceImpl` pres `Long.compare`)
   - Seradit `List<PlayerLeaderboardResponse>` podle `eloRating DESC`
     (`stream().sorted(Comparator.comparingDouble(PlayerLeaderboardResponse::getEloRating).reversed()).toList()`)
   - Doplnit `LeaderboardController.getLeaderboard` (ted vraci `null`)
   - Pri nenalezene sezone hodit `SeasonNotFoundException` (uz mas)

2. **`POST /seasons/{id}/activate`** — aktivuje sezonu, deaktivuje vsechny ostatni,
   resetuje ELO vsech hracu na 1000. Anotuj `@Transactional` — atomicita je tu klicova
   (bud se zmeni vsechno, nebo nic).

3. **Polish** — pred Fazi 4 zvaz drobnosti z "Polish" sekce nize, hlavne switch
   expression v `MatchServiceImpl.calculateRating`.

---

### Faze 1 — hotovo

- [x] Spring Boot projekt s pom.xml, parent, zavislosti
- [x] `application.yml` s H2 konfiguraci
- [x] `Main.java` s `@SpringBootApplication`
- [x] Entity `Player` a `Season` s JPA anotacemi
- [x] Repository vrstva (`PlayerRepository`, `SeasonRepository`)
- [x] REST controllery s CRUD endpointy

### Faze 2 — hotovo

**Entity (`@CreationTimestamp` kde davalo smysl):**
- [x] `Team` — id, name, abbreviation (max 5), foundedAt
- [x] `Match` — `@ManyToOne` na playerHome, playerAway, season; scoreHome/Away (Long), playedAt
- [x] `PlayerTeam` — `@ManyToOne` na player+team, joinedAt, `@Nullable leftAt`

**DTO vrstva:**
- [x] Request/Response DTOs pro vsechny entity, controllery vraceji jen DTOs

**Mapper vrstva (nad ramec puvodniho zadani):**
- [x] Interface + Impl pro Player, Season, Team, PlayerTeam, Match

**Service vrstva (nad ramec puvodniho zadani):**
- [x] Interface + Impl pro Player, Season, Team, PlayerTeam, Match

**Endpointy (Team a clenstvi):**
- [x] `POST /teams`
- [x] `POST /teams/{teamId}/members` — kontroluje, ze hrac neni v jinem aktivnim tymu (`PlayerAlreadyInTeamException`)
- [x] `DELETE /teams/{teamId}/members/{playerId}` — soft delete (`leftAt = LocalDate.now()`)
- [x] `GET /teams/{teamId}/members` — aktivni clenove (filtrovano `leftAt IS NULL`)

**Specialni dotazy:**
- [x] `PlayerTeamRepository`: `findByTeamIdAndLeftAtIsNull`, `findByPlayerIdAndLeftAtIsNull`, `findByPlayerIdAndTeamIdAndLeftAtIsNull`
- [x] `SeasonRepository.findByActiveIsTrue`

**Sezony — auto-aktivace pri vytvoreni:**
- [x] `SeasonServiceImpl.createSeason` automaticky nastavi `active = true`, pokud zadna aktivni neexistuje a datumy obklopuji dnesek (`!startDate.isAfter(today) && !endDate.isBefore(today)` — inkluzivni)

---

## Faze 3 — Business logika (rozpracovana, ~70 %)

### Co je hotovo

**ELO vypocet:**
- [x] `MatchResult` enum (`WIN`, `LOSS`, `DRAW`)
- [x] `MatchServiceImpl.calculateRating` — **pure funkce**: bere `Double playerRating, Double opponentRating, MatchResult`, vraci novy rating
- [x] **Zero-sum**: `createMatch` zachytava oba puvodni ratingy do lokalnich promennych pred kalkulaci, oba hraci pouzivaji pre-match hodnoty (oprava puvodniho bugu, kde druhy vypocet bral uz updatovany rating prvniho hrace)
- [x] `K = 32`, `S = {1, 0.5, 0}` podle vzorce v zadani

**Match endpoint (`POST /matches`):**
- [x] Validace v poradi: same-player check (fail-fast bez DB) → load hracu (`PlayerNotFoundException`) → load aktivni sezony (`SeasonNotActiveException`) → kalkulace → save
- [x] `@Transactional` zarucuje atomicitu (vsechny zmeny commitnou spolecne, nebo nic)
- [x] Match vyrobeny pres `MatchMapper`, pripojena aktivni sezona

**Vlastni vyjimky:**
- [x] `SamePlayerException`, `SeasonNotActiveException`
- [x] `PlayerAlreadyInTeamException`, `PlayerNotPartOfTeamException`
- [x] `PlayerNotFoundException`, `SeasonNotFoundException`, `TeamNotFoundException` (zapojeno ve vsech `findById().orElseThrow(...)` volanich)

**`GlobalExceptionHandler` — kompletni:**
- [x] `SamePlayerException` → HTTP 400
- [x] `PlayerAlreadyInTeamException`, `PlayerNotPartOfTeamException`, `SeasonNotActiveException` → HTTP 409
- [x] `PlayerNotFoundException`, `SeasonNotFoundException`, `TeamNotFoundException` → HTTP 404

**Leaderboard — pouze kostra:**
- [x] `LeaderboardController` se signaturou `GET /leaderboard?seasonId=X` (vraci `null`)
- [x] DTO `LeaderboardResponse` (Season + List<PlayerLeaderboardResponse>) s `@Data @NoArgsConstructor`
- [x] DTO `PlayerLeaderboardResponse` (totalMatches, wins, losses, draws) s `@Data @NoArgsConstructor`
- [x] `LeaderboardService` interface + `LeaderboardServiceImpl` (`@Service`, ale zatim prazdne)

### Co zbyva dodelat

**Leaderboard — dokoncit logiku** (viz "Kde pokracovat zitra" vyse pro detaily)

**Sprava sezon:**
- [ ] `POST /seasons/{id}/activate` — aktivuje sezonu, deaktivuje vsechny ostatni (`@Transactional`)
- [ ] Pri aktivaci nove sezony se ELO vsech hracu resetuje na 1000 (v ramci stejne transakce)

---

## Polish (volitelne pred Fazi 4)

Drobnosti, ktere nejsou bugy, ale stoji za pozornost az budes mit cas:

- **`MatchServiceImpl.createMatch`** — explicitni `playerRepository.save(playerHome/Away)`
  jsou redundantni. Metoda je `@Transactional`, takze dirty checking flushne zmeny
  `setEloRating` automaticky pri commitu. `matchRepository.save(match)` ale potreba je
  (novy entity, dosud detached).
- **`MatchServiceImpl.calculateRating`** — reassignment parametru `playerRating` v
  `switch`. Cistsi je switch expression (Java 14+):

  ```java
  double s = switch (result) {
      case WIN -> 1.0;
      case DRAW -> 0.5;
      case LOSS -> 0.0;
  };
  return playerRating + 32.0 * (s - likelihoodOfWin);
  ```

- **`MatchServiceImpl.calculateRating`** — boxed `Double` v privatni vypocetni metode
  by mohl byt primitive `double` (mensi alokace, zadne unboxing NPE).
- **Endpointy** zatim bez `/api/` prefixu (puvodne se s nim pocitalo) — ve Fazi 4 zvazit sjednoceni.

---

## Faze 4 — Production-ready veci

1. **Validace** — Bean Validation (`@Valid`, `@NotBlank`, `@Email`, `@Size`, custom validatory)
2. **Error handling** — sjednoceny error response format (napr. `{timestamp, status, message, path}`), pokryti vsech vyjimek
3. **Strankovani a razeni** — `GET /players?page=0&size=10&sort=eloRating,desc` (Pageable)
4. **Filtrovani** — `GET /matches?seasonId=1&playerId=5`
5. **Auditing** — `@CreatedDate`, `@LastModifiedDate`, `@EntityListeners(AuditingEntityListener.class)`
6. **Testy** — unit testy pro service vrstvu (Mockito), integracni testy controlleru (`@SpringBootTest`, `MockMvc`)

---

## Pravidla

- Pise sam, bez AI generovani kodu — pouze konzultace konceptu a code review
- Po kazde fazi commit a review
- Pokud zasek > 30 minut, popsat co zkousel a kde presne je problem
