package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_sessions")
data class PracticeSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val mantraId: String,
    val mantraTitle: String,
    val repetitionsCompleted: Int,
    val durationSeconds: Int,
    val timestamp: Long = System.currentTimeMillis()
)
