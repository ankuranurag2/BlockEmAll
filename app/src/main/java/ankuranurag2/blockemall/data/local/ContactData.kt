package ankuranurag2.blockemall.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class ContactData(
    @PrimaryKey
    val number: String,
    val name: String?
)