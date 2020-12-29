package ankuranurag2.blockemall.ui

import androidx.lifecycle.*
import ankuranurag2.blockemall.data.local.ContactData
import ankuranurag2.blockemall.repository.ContactRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ContactRepository) : ViewModel() {

    var contactLiveData: LiveData<List<ContactData>> =
        repository.getContacts().asLiveData(viewModelScope.coroutineContext)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun addContact(contactData: ContactData) {
        viewModelScope.launch(exceptionHandler) {
            repository.addContact(contactData)
        }
    }
}