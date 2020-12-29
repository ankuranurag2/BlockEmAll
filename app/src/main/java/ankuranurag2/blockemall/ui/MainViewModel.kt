package ankuranurag2.blockemall.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ankuranurag2.blockemall.data.local.ContactData
import ankuranurag2.blockemall.repository.ContactRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ContactRepository) : ViewModel() {

    var contactLiveData: LiveData<List<ContactData>> =
        repository.getContacts().asLiveData(viewModelScope.coroutineContext)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun addContact(contactData: ContactData) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.addContact(contactData)
        }
    }

    fun deleteContact(contactData: ContactData) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.deleteContact(contactData)
        }
    }
}