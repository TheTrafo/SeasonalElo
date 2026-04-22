# Ranked League System — Zadani a postup

## O projektu

Trenovaci projekt pro pripravu na pohovory. Cil: napsat kompletni Spring Boot REST API
od nuly, bez pomoci AI (pouze konzultace konceptu).

Tech stack: Java 21, Spring Boot, Spring Data JPA, Hibernate, H2 databaze.

---

## Aktualni stav: Faze 1 hotova, Faze 2 nezacata

### Co je hotovo (Faze 1)

- [x] Spring Boot projekt s pom.xml, parent, zavislosti
- [x] `application.yml` s H2 konfiguraci
- [x] `Main.java` s `@SpringBootApplication`
- [x] Entity `Player` a `Season` s JPA anotacemi (`@GeneratedValue`, `@Column(unique=true)`, no-arg konstruktory)
- [x] Repository vrstva (`PlayerRepository`, `SeasonRepository`)
- [x] REST controllery s CRUD endpointy (`/players`, `/seasons`)
- [x] DTO tridy vytvoreny (Create, Update, Return pro Player i Season) — ale ZATIM NEJSOU ZAPOJENE do controlleru
- [x] Prazdne entity `Team`, `Match`, `PlayerTeamMembership` pripraveny jako shell

### Drobne veci k oprave (pred nebo behem faze 2)

- `ReturnPlayerDTO` a `ReturnSeasonDTO` nemaji `id` field — pridat
- `ReturnPlayerDTO` nema `registeredAt` — pridat, a nastavovat automaticky pri vytvoreni hrace
- Nepouzite importy v `SeasonController` (Optional) a `ReturnPlayerDTO` (LocalDate)
- Controllery porad prijimaji a vraci entity primo — prepnout na DTOs je hlavni ukol faze 2

---

## Faze 2 — Vztahy a DTOs

### Nove entity

**Team**
- `id`, `name`, `abbreviation` (max 5 znaku), `foundedAt`

**Match**
- `id`, `playerHome` (ManyToOne Player), `playerAway` (ManyToOne Player), `scoreHome`, `scoreAway`, `playedAt`, `season` (ManyToOne Season)

**PlayerTeamMembership** (join tabulka s extra daty)
- `id`, `player` (ManyToOne Player), `team` (ManyToOne Team), `joinedAt`, `leftAt` (nullable — null = stale aktivni)

### JPA vztahy

- `Match` → `@ManyToOne` na `Player` (home/away) a `Season`
- `PlayerTeamMembership` → `@ManyToOne` na `Player` i `Team`

### DTO vrstva

- Entity nikdy nevracet primo v REST response
- Vytvorit request/response DTOs a mapovani mezi nimi
- Zapojit existujici DTOs do `PlayerController` a `SeasonController`
- Vytvorit DTOs pro nove entity

### Endpointy

- `POST /api/teams` — vytvoreni tymu
- `POST /api/teams/{teamId}/members` — pridani hrace do tymu
- `DELETE /api/teams/{teamId}/members/{playerId}` — odebrani (nastavi `leftAt`)
- `GET /api/teams/{teamId}/members` — aktualni clenove

---

## Faze 3 — Business logika

### Zaznam zapasu — `POST /api/matches`

- Validace: hrac nemuze hrat sam proti sobe, oba hraci musi existovat, sezona musi byt aktivni
- Po ulozeni zapasu automaticky prepocitej ELO obou hracu

### ELO vypocet (implementovat v service vrstve)

```
Ocekavana pravdepodobnost:  E_a = 1 / (1 + 10^((R_b - R_a) / 400))
Novy rating:                R_a' = R_a + K * (S_a - E_a)
K = 32, S_a = 1 (vyhra), 0.5 (remiza), 0 (prohra)
```

### Zebricek — `GET /api/leaderboard?seasonId=X`

- Hrace serazene podle ELO
- Statistika: pocet zapasu, vyher, proher, remiz

### Sprava sezon

- `POST /api/seasons/{id}/activate` — aktivuje sezonu (deaktivuje vsechny ostatni)
- Pri startu nove sezony se ELO vsech hracu resetuje na 1000

---

## Faze 4 — Production-ready veci

1. **Validace** — Bean Validation (`@Valid`, `@NotBlank`, `@Email`, `@Size`, custom validatory)
2. **Error handling** — `@ControllerAdvice` s vlastnimi vyjimkami (`PlayerNotFoundException`, `InvalidMatchException`, `SeasonNotActiveException`), konzistentni error response format
3. **Strankovani a razeni** — `GET /api/players?page=0&size=10&sort=eloRating,desc`
4. **Filtrovani** — `GET /api/matches?seasonId=1&playerId=5`
5. **Auditing** — `@CreatedDate`, `@LastModifiedDate` na entitach

---

## Pravidla

- Pise sam, bez AI generovani kodu — pouze konzultace konceptu a code review
- Po kazde fazi commit a review
- Pokud zasek > 30 minut, popsat co zkousel a kde presne je problem
