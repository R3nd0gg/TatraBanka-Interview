package sk.tatrabanka.masarykapp.viewmodel

import androidx.lifecycle.ViewModel
import sk.tatrabanka.masarykapp.repository.UsersRepository

class UserListViewModel : ViewModel() {
    private val usersRepository = UsersRepository.instance
    val usersExhaustedObservable = usersRepository.usersExhaustedObservable
    val usersObservable = usersRepository.usersObservable

    fun clearCacheAndFetch() {
        usersRepository.clearCacheAndFetch()
    }

    fun fetchNextPage() {
        usersRepository.fetchNextPage()
    }
}