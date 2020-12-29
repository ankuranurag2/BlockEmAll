package ankuranurag2.blockemall.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ankuranurag2.blockemall.data.local.ContactData
import ankuranurag2.blockemall.repository.ContactRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ContactRepository) : ViewModel() {

    val contactLiveData = MutableLiveData<List<ContactData>>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun addContact(contactData: ContactData) {
        viewModelScope.launch(exceptionHandler) {
            repository.addContact(contactData)
        }
    }

    fun fetchContacts() {
        viewModelScope.launch(exceptionHandler) {
            val contactFlow = repository.getContacts()
            contactLiveData.postValue(contactFlow.asLiveData().value)
        }
    }
}