package ankuranurag2.blockemall.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_table")
    fun getContactsAsFlow(): Flow<List<ContactData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactInDb(contactData: ContactData)
}