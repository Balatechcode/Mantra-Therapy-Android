# Integration of Authentic 19-Mantra Catalog

Integrating the complete collection of 19 authentic mantras provided in the user's JSON dataset into the Mantra Therapy Android app, replacing sample mantras and establishing purpose-driven categorizations, accurate Devanagari and Roman transliterations, and Japa round tracking.

## User Review & Critical Decisions

> [!IMPORTANT]
> The following user preferences were confirmed during Phase 1 clarification:

- **Integration Mode**: Replaces initial sample placeholder mantras with the complete, curated 19-mantra collection from Acharya Mahapragya's sacred literature.
- **Organization & Filtering**: Grouped by spiritual purpose and problem resolution (Fortune, Fame, Success, Concentration, Vachan Siddhi, Wealth, Willpower, Auspiciousness) with quick filter chips.
- **Japa Counter Architecture**: High-repetition practices (such as 1,000 repetitions or 125,000 for mantra-siddhi) are tracked using standard 108-bead rosary rounds with an overarching siddhi progression counter.

---

## 1. Overview & Core Concept

- **What It Does**: Populates the local Room database with 19 authentic mantras covering daily auspiciousness, obstacle removal, speech empowerment (Vachan Siddhi), mental stillness, and prosperity.
- **Target Audience**: Dedicated sadhakas, practitioners of Preksha Dhyana, Jain contemplation, and seekers of sacred vibration therapy.
- **Key Value**: Provides an unabridged, offline digital scripture with authentic dual-script presentation (Devanagari script + IAST Roman phonetic transliteration), specific bodily focus centers (Darshan Kendra, Anand Kendra), and structured counting.

---

## 2. User Experience & Visual Design

### Key User Flows

1. **Browse & Filter**: The Home screen shows purpose filter chips: `All`, `Fortune`, `Fame`, `Success`, `Concentration`, `Vachan Siddhi`, `Wealth`, and `Auspiciousness`.
2. **Explore Sacred Scripture**: Cards display Devanagari verses in elegant serif calligraphy, subtitle/problem addressed, repetition count, and purpose badge.
3. **Mantra Details**: Displays the full Devanagari verse, phonetic pronunciation guide, associated problem, sacred procedure (e.g. bodily focus centers, morning sunrise timing, palm movements), and spiritual benefit.
4. **Mala Bead Counter (Japa)**: Tapping the sacred Mala counter advances individual beads (1 to 108) and automatically tallies completed rosary rounds toward the grand target.

### Visual Identity & Theme Tokens

- **Palette**: Warm Sandalwood (`#8C4A2F`), Terracotta Gold (`#D4A373`), Sacred Parchment (`#FDFBF7`), Pristine Vellum (`#FFFDF9`), Deep Umber (`#2C1D11`).
- **Typography**: Classical Serif for Devanagari chants and English titles; clean geometric sans for instructions, counts, and metadata.
- **Atmosphere**: Fine sandstone borders (1.dp), gold accents, and subtle lotus watermarks honoring authentic Indian ascetic aesthetics.

---

## 3. Key Product Decisions & Trade-Offs

### Decision 1: Database Migration & Clean Seeding
- **Approach**: Update `AppDatabase` seed routine to clear outdated placeholder sample entries and populate the 19 authentic mantras with deterministic IDs (`mantra_01_namaskar` through `mantra_19_materialistic_prosperity_2`).
- **Why**: Ensures clean catalog consistency without orphaned or duplicate placeholder records while preserving session history and practitioner profile settings.

### Decision 2: Dual-Mode Repetition Tracking
- **Approach**: For mantras with short breath counts (e.g. Mantra 02 with 21 or 32 repetitions in one breath), set `targetRepetitions` to 32. For rosary and siddhi mantras, maintain standard 108 beads with a "Rosary Rounds" descriptor for 1,000 or 125,000 masteries.
- **Why**: Keeps the tactile mala bead counter intuitive and responsive while honoring the scriptural guidelines for long-term sadhana.

---

## 4. Technical Architecture & Data Strategy

```
┌─────────────────────────────────────────────────────────────┐
│                    Practitioner UI Layer                    │
│                                                             │
│   HomeScreen           SearchScreen         MantraDetails   │
│   [Purpose Chips]      [Full Text Search]   [Dual Script]   │
│         │                     │                    │        │
│         ▼                     ▼                    ▼        │
│   PracticeJapaScreen (108-Bead Mala + Rosary Rounds)        │
└──────────────────────────────┬──────────────────────────────┘
                               │ StateFlow & Events
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                       MantraViewModel                       │
│   - allMantras: StateFlow<List<MantraEntity>>               │
│   - selectedPurposeChip: StateFlow<String>                  │
│   - japaCount & currentRosaryRounds                         │
└──────────────────────────────┬──────────────────────────────┘
                               │ Room Coroutines / Flow
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                       MantraRepository                      │
│   - seedMantras: 19 Authentic Json Entries                  │
│   - syncSeedData(): Inserts / Updates all 19 Mantras        │
└──────────────────────────────┬──────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                SQLite / Room Local Database                 │
│   - Table: mantras (19 records with devanagari & details)   │
│   - Table: practice_sessions (persisted history)            │
└─────────────────────────────────────────────────────────────┘
```

### Component & Entity Mapping

| ID | Title | Purpose Group | Target / Repetitions | Key Procedure / Focus |
|---|---|---|---|---|
| `mantra_01` | Namaskar Mahamantra | Auspiciousness | 108 | General welfare and supreme obeisance |
| `mantra_02` | An Auspicious Beginning | Auspiciousness | 32 | Sunrise chant in one breath |
| `mantra_03` | Obtaining the Rare Thing | Fulfillment | 108 | One rosary daily |
| `mantra_04` | Attaining Success | Success | 108 | Bhaktamara first stanza, facing East |
| `mantra_05` | Resolution Power | Willpower | 108 | Focus at Darshan Kendra, deep breaths |
| `mantra_06` | Concentration | Concentration | 108 | Syllable focus: Navel, Head, Throat, Heart, Face |
| `mantra_07` | Augmentation of Good Fortune 1 | Fortune | 108 | One rosary daily for health & prosperity |
| `mantra_08` | Augmentation of Good Fortune 2 | Fortune | 21 | 21 times, move palms over face |
| `mantra_09` | Augmentation of Good Fortune 3 | Fortune | 108 | One rosary daily |
| `mantra_10` | Augmentation of Fame 1 | Fame | 108 | Rosary daily; 125,000 for mantra-siddhi |
| `mantra_11` | Augmentation of Fame 2 | Fame | 108 | 1,000 repetitions for wish fulfillment |
| `mantra_12` | Vachan Siddhi 1 | Vachan Siddhi | 108 | One rosary daily for truthful speech |
| `mantra_13` | Vachan Siddhi 2 | Vachan Siddhi | 108 | Ajapa-japa mental chanting for six months |
| `mantra_14` | Vachan Siddhi 3 | Vachan Siddhi | 108 | Saraswati invocation; 125,000 for siddhi |
| `mantra_15` | Vachan Siddhi 4 | Vachan Siddhi | 108 | Om Shaantih 3x daily (9 hours total) |
| `mantra_16` | Vachan Siddhi 5 | Vachan Siddhi | 108 | 1,211 repetitions for honour & fearlessness |
| `mantra_17` | Ability of Becoming Arhat | Spiritual | 108 | Concentrate at Anand Kendra |
| `mantra_18` | Materialistic Prosperity 1 | Wealth | 108 | Resolves lack of prosperity |
| `mantra_19` | Materialistic Prosperity 2 | Wealth | 108 | Resolves material problems & dispels hardship |
