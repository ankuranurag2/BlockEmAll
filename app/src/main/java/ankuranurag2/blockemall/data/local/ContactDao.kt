package ankuranurag2.blockemall.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_table")
    fun getContactsAsFlow(): Flow<List<ContactData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactInDb(contactData: ContactData)

    @Delete
    suspend fun deleteContact(contactData: ContactData)
}