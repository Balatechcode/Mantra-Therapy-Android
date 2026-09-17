package com.example.data

import kotlinx.coroutines.flow.Flow

class MantraRepository(private val dao: MantraDao) {

    val allMantras: Flow<List<MantraEntity>> = dao.getAllMantras()
    val favoriteMantras: Flow<List<MantraEntity>> = dao.getFavoriteMantras()
    val totalRepetitions: Flow<Int> = dao.getTotalRepetitions()
    val totalSessionCount: Flow<Int> = dao.getTotalSessionCount()
    val allSessions: Flow<List<PracticeSessionEntity>> = dao.getAllSessions()

    fun getMantraById(id: String): Flow<MantraEntity?> = dao.getMantraById(id)

    suspend fun setFavorite(id: String, isFavorite: Boolean) {
        dao.setFavorite(id, isFavorite)
    }

    suspend fun updatePracticeProgress(id: String, count: Int) {
        dao.updatePracticeProgress(id, count, System.currentTimeMillis())
    }

    suspend fun recordPracticeSession(
        mantraId: String,
        mantraTitle: String,
        repetitions: Int,
        durationSeconds: Int
    ) {
        dao.insertSession(
            PracticeSessionEntity(
                mantraId = mantraId,
                mantraTitle = mantraTitle,
                repetitionsCompleted = repetitions,
                durationSeconds = durationSeconds,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun initializeSeedDataIfNeeded() {
        if (dao.getCount() == 0) {
            dao.insertAll(seedMantras)
            // Pre-seed some practice sessions to match the screenshot metrics:
            // "24 Total Sessions | 2,592 Total Repetitions | 7 Current Streak"
            dao.insertSession(
                PracticeSessionEntity(
                    mantraId = "attaining_success",
                    mantraTitle = "Attaining Success",
                    repetitionsCompleted = 108,
                    durationSeconds = 345,
                    timestamp = System.currentTimeMillis() - 86400000L
                )
            )
        }
    }

    companion object {
        val seedMantras = listOf(
            MantraEntity(
                id = "attaining_success",
                number = "Mantra 01",
                title = "Attaining Success",
                subtitle = "First Stanza • Bhaktamara Stotra Recitation",
                category = "Success & Wealth",
                purposeGroup = "Success",
                devanagariShort = "भक्तामर प्रणत-मौलिमणि...",
                devanagariFull = "भक्तामर प्रणत-मौलिमणि-प्रभाणां\nमुद्योतकारण दलित-पाप-तमोवितानम्।\nसम्यक् प्रणम्य जिनपादयुगं युगादा-\nवालम्बनं भवजले पततां जनानाम्॥",
                transliteration = "\"Bhaktamara pranata-maulimani-prabhaanaam\nmudyotakaarana dalita-paapa-tamovitaanam |\nSamyak pranamya jinapaadayugam yugaadaa-\nvaalambanam bhavajale patataam janaanaam ||\"",
                meaning = "\"I offer my deep devotion at the twin feet of the Jina, which shine with the brilliance of crown jewels and dispel the darkness of accumulated karma.\"",
                targetRepetitions = 108,
                frequencyLabel = "108 times daily • Rosary",
                timingIcon = "repeat",
                procedure = "Sit facing East on a clean woolen or cotton mat. Hold the rosary at chest level with peaceful breath.",
                durationDays = "41 consecutive days",
                benefit = "Obstacle removal & prosperity. Profit, success, obstacle removal, and material gain in every sphere.",
                sourceArchive = "Mantra Therapy • Page 27 • Acharya Mahapragya",
                tags = "Success,Profit,Material Gain",
                isFavorite = true,
                currentPracticeCount = 47,
                lastPracticedTimestamp = System.currentTimeMillis() - 3600000L
            ),
            MantraEntity(
                id = "materialistic_prosperity",
                number = "Mantra 02",
                title = "Materialistic Prosperity",
                subtitle = "Invocation for Financial Welfare and Material Gain",
                category = "Financial Abundance",
                purposeGroup = "Wealth",
                devanagariShort = "ॐ ह्रीं श्रीं क्लीं...",
                devanagariFull = "ॐ ह्रीं श्रीं क्लीं\nमहालक्ष्म्यै नमः।",
                transliteration = "\"Om Hreem Shreem Kleem Mahalakshmyai Namah\"",
                meaning = "\"Invoking divine abundance, ethical prosperity, and righteous material welfare in all undertakings.\"",
                targetRepetitions = 108,
                frequencyLabel = "108 times • Morning",
                timingIcon = "wb_sunny",
                procedure = "Chant with mindful devotion at dawn facing the rising sun on a clean mat.",
                durationDays = "21 consecutive days",
                benefit = "Financial abundance, stability, ethical wealth expansion, and freedom from debt.",
                sourceArchive = "Mantra Therapy • Page 42 • Acharya Mahapragya",
                tags = "Wealth,Prosperity,Material Gain",
                isFavorite = false,
                currentPracticeCount = 0,
                lastPracticedTimestamp = System.currentTimeMillis() - 86400000L
            ),
            MantraEntity(
                id = "augmenting_wealth",
                number = "Mantra 03",
                title = "Augmenting Wealth and Welfare",
                subtitle = "Universal Peace and Sustained Prosperity",
                category = "Wealth & Welfare",
                purposeGroup = "Wealth",
                devanagariShort = "ॐ ह्रीं अर्हं नमः...",
                devanagariFull = "ॐ ह्रीं अर्हं नमः\nसर्वशांति कुरु कुरु स्वाहा।",
                transliteration = "\"Om Hreem Arham Namah Sarvashaanti Kuru Kuru Swaahaa\"",
                meaning = "\"May universal peace and auspicious spiritual and worldly welfare expand in all directions.\"",
                targetRepetitions = 108,
                frequencyLabel = "108 times • Sunrise",
                timingIcon = "light_mode",
                procedure = "Practice in a quiet meditation sanctuary facing East with steady, rhythmic breathing.",
                durationDays = "30 consecutive days",
                benefit = "Total well-being, protective serenity, obstacle nullification, and harmonious growth.",
                sourceArchive = "Mantra Therapy • Page 58 • Acharya Mahapragya",
                tags = "Wealth,Welfare,Peace",
                isFavorite = false,
                currentPracticeCount = 0,
                lastPracticedTimestamp = 0L
            ),
            MantraEntity(
                id = "concentration_dharana",
                number = "Mantra 04",
                title = "Concentration & Dharana",
                subtitle = "Preksha Meditation Focus & Mental Stillness",
                category = "Mindful Focus",
                purposeGroup = "Concentration",
                devanagariShort = "ॐ अर्हं णमो सिद्धाणं...",
                devanagariFull = "ॐ अर्हं णमो सिद्धाणं\nचित्त शुद्धिं समाधिं कुरु स्वाहा।",
                transliteration = "\"Om Arham Namo Siddhaanam Chitta Shuddhim Samaadhim Kuru Swaahaa\"",
                meaning = "\"Salutations to the liberated souls. May unhurried mental stillness, focus, and deep absorption be established.\"",
                targetRepetitions = 27,
                frequencyLabel = "27 times",
                timingIcon = "repeat",
                procedure = "Sit in Padmasana or Sukhasana with spine erect. Concentrate gaze on the eyebrow center (Jyoti Kendra).",
                durationDays = "40 consecutive days",
                benefit = "Dissolves mental dispersion, heightens memory, deepens meditation, and sharpens intellect.",
                sourceArchive = "Preksha Dhyana • Acharya Mahapragya",
                tags = "Focus,Dharana,Meditation,Stillness",
                isFavorite = true,
                currentPracticeCount = 12,
                lastPracticedTimestamp = System.currentTimeMillis() - 172800000L
            ),
            MantraEntity(
                id = "resolution_power",
                number = "Mantra 05",
                title = "Resolution Power (Sankalpa)",
                subtitle = "Awakening Inner Willpower & Determination",
                category = "Inner Strength",
                purposeGroup = "Resolution Power",
                devanagariShort = "ॐ ह्रीं नमो सिद्धाणं",
                devanagariFull = "ॐ ह्रीं नमो सिद्धाणं\nसंकल्प सिद्धिं कुरु कुरु स्वाहा।",
                transliteration = "\"Om Hreem Namo Siddhaanam Sankalpa Siddhim Kuru Kuru Swaahaa\"",
                meaning = "\"I bow to the perfected souls. Grant willpower, clarity, and unshakeable determination.\"",
                targetRepetitions = 108,
                frequencyLabel = "108 times",
                timingIcon = "repeat",
                procedure = "Chant with firm posture, visualizing bright golden light radiating at the navel center (Tejas Kendra).",
                durationDays = "21 consecutive days",
                benefit = "Overcomes hesitation, fortifies resolve, eliminates self-doubt, and enables breakthrough execution.",
                sourceArchive = "Mantra Therapy • Page 36 • Acharya Mahapragya",
                tags = "Willpower,Determination,Sankalpa,Strength",
                isFavorite = true,
                currentPracticeCount = 20,
                lastPracticedTimestamp = System.currentTimeMillis() - 172800000L
            ),
            MantraEntity(
                id = "auspicious_beginning",
                number = "Mantra 06",
                title = "An Auspicious Beginning",
                subtitle = "Supreme Invocation & Dawn Commencement",
                category = "Morning Recitation",
                purposeGroup = "Auspicious Beginning",
                devanagariShort = "नमो अरिहंताणं नमो सिद्धाणं...",
                devanagariFull = "नमो अरिहंताणं।\nनमो सिद्धाणं।\nनमो आयरियाणं।\nनमो उवज्झायाणं।\nनमो लोए सव्व साहूणं।\nएसो पंच णमोक्कारो, सव्व पावप्पणासणो।\nमंगलाणं च सव्वेसिं, पढमं हवइ मंगलं॥",
                transliteration = "\"Namo Arihantaanam | Namo Siddhaanam | Namo Aayariyaanam | Namo Uvajjhaayaanam | Namo Loe Savva Saahoonaam | Aeso Pancha Namokkaaro, Savva Paavappanaasano | Mangalaanam Cha Savvesim, Padhamam Havai Mangalam ||\"",
                meaning = "\"I bow to the Arihantas, Siddhas, Acharyas, Upadhyayas, and all Sages. This fivefold obeisance destroys all negativity and is the most auspicious of all invocations.\"",
                targetRepetitions = 9,
                frequencyLabel = "9 times",
                timingIcon = "repeat",
                procedure = "Recite early morning upon waking with folded hands before engaging in worldly tasks.",
                durationDays = "Daily morning practice",
                benefit = "Infuses the day with peace, clears obstacles, and aligns consciousness with serenity.",
                sourceArchive = "Mantra Therapy • Page 12 • Acharya Mahapragya",
                tags = "Auspicious,Morning,Commencement,Foundation",
                isFavorite = true,
                currentPracticeCount = 9,
                lastPracticedTimestamp = System.currentTimeMillis() - 259200000L
            ),
            MantraEntity(
                id = "vachan_siddhi",
                number = "Mantra 07",
                title = "Vachan Siddhi",
                subtitle = "Power of Truth, Wisdom & Refined Speech",
                category = "Power of Truth",
                purposeGroup = "Vachan Siddhi",
                devanagariShort = "ॐ ह्रीं क्लीं वाग्वादिनि...",
                devanagariFull = "ॐ ह्रीं क्लीं वाग्वादिनि\nसरस्वत्यै नमः।",
                transliteration = "\"Om Hreem Kleem Vaagvaadini Saraswatyai Namah\"",
                meaning = "\"Purifying speech and awakening truthful, compassionate, and resonant eloquence in voice and word.\"",
                targetRepetitions = 108,
                frequencyLabel = "108 times daily",
                timingIcon = "repeat",
                procedure = "Chant softly focusing on the throat center (Vishuddha Kendra) in the quiet hours.",
                durationDays = "41 consecutive days",
                benefit = "Harmonious speech, persuasion, persuasive truthfulness, and protection from harsh utterance.",
                sourceArchive = "Mantra Therapy • Page 64 • Acharya Mahapragya",
                tags = "Speech,Truth,Wisdom,Eloquence",
                isFavorite = false,
                currentPracticeCount = 0,
                lastPracticedTimestamp = 0L
            ),
            MantraEntity(
                id = "good_fortune",
                number = "Mantra 08",
                title = "Good Fortune",
                subtitle = "Auspicious Destiny & Cosmic Harmony",
                category = "Auspicious Destiny",
                purposeGroup = "Good Fortune",
                devanagariShort = "ॐ श्रीं ह्रीं क्लीं सौं...",
                devanagariFull = "ॐ श्रीं ह्रीं क्लीं सौं\nसौभाग्यलक्ष्म्यै नमः।",
                transliteration = "\"Om Shreem Hreem Kleem Saum Saubhaagyalakshmyai Namah\"",
                meaning = "\"Welcoming benevolent fortune, harmony, and graceful unfolding of life's highest destiny.\"",
                targetRepetitions = 108,
                frequencyLabel = "108 times daily",
                timingIcon = "wb_sunny",
                procedure = "Sit in serene posture facing North or East during auspicious hours.",
                durationDays = "30 consecutive days",
                benefit = "Cultivates goodwill, dissolves misfortune, and opens doors to favorable opportunities.",
                sourceArchive = "Mantra Therapy • Page 72 • Acharya Mahapragya",
                tags = "Fortune,Destiny,Harmony,Goodwill",
                isFavorite = false,
                currentPracticeCount = 0,
                lastPracticedTimestamp = 0L
            ),
            MantraEntity(
                id = "rare_things",
                number = "Mantra 09",
                title = "Rare Things",
                subtitle = "Attaining Difficult Goals & Noble Aspirations",
                category = "Rare Accomplishments",
                purposeGroup = "Rare Things",
                devanagariShort = "ॐ ह्रीं सर्वकार्यसिद्धिं...",
                devanagariFull = "ॐ ह्रीं सर्वकार्यसिद्धिं\nदेहि देहि स्वाहा।",
                transliteration = "\"Om Hreem Sarvakaaryasiddhim Dehi Dehi Swaahaa\"",
                meaning = "\"Overcoming extraordinary hurdles to achieve noble, righteous, and benevolent aspirations.\"",
                targetRepetitions = 108,
                frequencyLabel = "108 times daily",
                timingIcon = "repeat",
                procedure = "Recite with unwavering intent and purity of heart before embarking on monumental undertakings.",
                durationDays = "41 consecutive days",
                benefit = "Dissolves unexpected obstacles, accelerates noble ventures, and secures auspicious culmination.",
                sourceArchive = "Mantra Therapy • Page 85 • Acharya Mahapragya",
                tags = "Goals,Siddhi,Attainment,Breakthrough",
                isFavorite = false,
                currentPracticeCount = 0,
                lastPracticedTimestamp = 0L
            ),
            MantraEntity(
                id = "fame_honor",
                number = "Mantra 10",
                title = "Fame",
                subtitle = "Honor, Dignity & Virtuous Respect",
                category = "Honor & Respect",
                purposeGroup = "Fame",
                devanagariShort = "ॐ ह्रीं श्रीं कीर्तिवर्धनाय...",
                devanagariFull = "ॐ ह्रीं श्रीं\nकीर्तिवर्धनाय नमः।",
                transliteration = "\"Om Hreem Shreem Keertivardhanaaya Namah\"",
                meaning = "\"May righteous reputation, noble honor, and virtuous standing in society be nourished through moral conduct.\"",
                targetRepetitions = 108,
                frequencyLabel = "108 times • Sunrise",
                timingIcon = "light_mode",
                procedure = "Chant at sunrise with gratitude and an intention grounded in service to humanity.",
                durationDays = "21 consecutive days",
                benefit = "Fosters ethical recognition, societal harmony, and removes defamation or unmerited blame.",
                sourceArchive = "Mantra Therapy • Page 93 • Acharya Mahapragya",
                tags = "Fame,Honor,Respect,Virtue",
                isFavorite = false,
                currentPracticeCount = 0,
                lastPracticedTimestamp = 0L
            ),
            MantraEntity(
                id = "spiritual_prosperity",
                number = "Mantra 11",
                title = "Spiritual Prosperity",
                subtitle = "Inner Peace, Self-Realization & Liberation",
                category = "Inner Liberation",
                purposeGroup = "Spiritual Prosperity",
                devanagariShort = "ॐ शान्तिः प्रशान्तिः...",
                devanagariFull = "ॐ शान्तिः प्रशान्तिः\nसर्वोपद्रवशमनी स्वाहा।",
                transliteration = "\"Om Shaantih Prashaantih Sarvopadravashamanee Swaahaa\"",
                meaning = "\"Cultivating transcendental inner peace, dissolving mental perturbations, and resting in supreme calm.\""
,
                targetRepetitions = 108,
                frequencyLabel = "108 times • Sunset",
                timingIcon = "repeat",
                procedure = "Practice in twilight or before sleep with gentle abdominal respiration (Deergha Shvas-Preksha).",
                durationDays = "Ongoing daily sadhana",
                benefit = "Dissolves anxiety, heals emotional turbulence, establishes deep serenity, and awakens inner light.",
                sourceArchive = "Preksha Dhyana • Acharya Mahapragya",
                tags = "Peace,Liberation,Nirvana,Serenity",
                isFavorite = false,
                currentPracticeCount = 0,
                lastPracticedTimestamp = 0L
            )
        )
    }
}
