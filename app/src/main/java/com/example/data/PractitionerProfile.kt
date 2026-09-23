package com.example.data

data class PractitionerProfile(
    val name: String = "Sadhaka Practitioner",
    val path: String = "Preksha Meditation & Japa Path",
    val practiceTime: String = "Brahma Muhurta Practice",
    val avatarSymbol: String = "ॐ",
    val dailyGoal: Int = 108,
    val sankalpa: String = "May my daily practice awaken inner stillness, obstacle removal, and supreme peace."
) {
    companion object {
        val AVATAR_OPTIONS = listOf("ॐ", "卐", "𑖌𑖼", "✦", "🪷", "☀️")
        val PATH_PRESETS = listOf(
            "Preksha Meditation & Japa Path",
            "Bhaktamara Stotra Sadhana",
            "Navkar Mahamantra Path",
            "Vedic & Tantric Chanting",
            "Mindful Breath & Contemplation"
        )
        val TIME_PRESETS = listOf(
            "Brahma Muhurta Practice",
            "Sunrise • Ushakal",
            "Noon • Madhyahna",
            "Sunset • Sandhya",
            "Evening • Nishitha"
        )
        val GOAL_OPTIONS = listOf(27, 54, 108, 216)
    }
}
