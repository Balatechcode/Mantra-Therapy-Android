package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mantras")
data class MantraEntity(
    @PrimaryKey val id: String,
    val number: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val purposeGroup: String,
    val devanagariShort: String,
    val devanagariFull: String,
    val transliteration: String,
    val meaning: String,
    val targetRepetitions: Int = 108,
    val frequencyLabel: String,
    val timingIcon: String = "repeat", // "repeat", "wb_sunny", "light_mode"
    val procedure: String,
    val durationDays: String = "41 consecutive days",
    val benefit: String,
    val sourceArchive: String = "Mantra Therapy • Page 27 • Acharya Mahapragya",
    val tags: String, // comma-separated
    val isFavorite: Boolean = false,
    val currentPracticeCount: Int = 0,
    val lastPracticedTimestamp: Long = 0L
)
