package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MantraDao {
    @Query("SELECT * FROM mantras ORDER BY number ASC")
    fun getAllMantras(): Flow<List<MantraEntity>>

    @Query("SELECT * FROM mantras WHERE isFavorite = 1 ORDER BY number ASC")
    fun getFavoriteMantras(): Flow<List<MantraEntity>>

    @Query("SELECT * FROM mantras WHERE id = :id LIMIT 1")
    fun getMantraById(id: String): Flow<MantraEntity?>

    @Query("SELECT * FROM mantras WHERE purposeGroup = :category OR category LIKE '%' || :category || '%'")
    fun getMantrasByCategory(category: String): Flow<List<MantraEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(mantras: List<MantraEntity>)

    @Update
    suspend fun update(mantra: MantraEntity)

    @Query("UPDATE mantras SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: String, isFavorite: Boolean)

    @Query("UPDATE mantras SET currentPracticeCount = :count, lastPracticedTimestamp = :timestamp WHERE id = :id")
    suspend fun updatePracticeProgress(id: String, count: Int, timestamp: Long)

    @Query("SELECT COUNT(*) FROM mantras")
    suspend fun getCount(): Int

    // Practice Sessions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: PracticeSessionEntity)

    @Query("SELECT * FROM practice_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<PracticeSessionEntity>>

    @Query("SELECT COALESCE(SUM(repetitionsCompleted), 0) FROM practice_sessions")
    fun getTotalRepetitions(): Flow<Int>

    @Query("SELECT COUNT(*) FROM practice_sessions")
    fun getTotalSessionCount(): Flow<Int>
}
