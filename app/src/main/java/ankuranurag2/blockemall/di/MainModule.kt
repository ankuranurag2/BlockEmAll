package ankuranurag2.blockemall.di

import ankuranurag2.blockemall.data.local.ContactDao
import ankuranurag2.blockemall.repository.ContactRepository
import ankuranurag2.blockemall.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel(get()) }
    single { getRepository(get()) }
}

private fun getRepository(dao: ContactDao) = ContactRepository(dao)