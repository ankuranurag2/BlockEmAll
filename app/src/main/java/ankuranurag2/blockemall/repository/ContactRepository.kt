package ankuranurag2.blockemall.repository

import ankuranurag2.blockemall.data.local.ContactDao
import ankuranurag2.blockemall.data.local.ContactData

class ContactRepository(private val dao: ContactDao) {

    fun getContacts() = dao.getContactsAsFlow()

    suspend fun addContact(contactData: ContactData) = dao.insertContactInDb(contactData)

    suspend fun deleteContact(contactData: ContactData) = dao.deleteContact(contactData)
}