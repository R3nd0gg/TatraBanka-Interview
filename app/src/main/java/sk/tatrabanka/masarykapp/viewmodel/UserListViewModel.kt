package sk.tatrabanka.masarykapp.viewmodel

import androidx.lifecycle.ViewModel
import sk.tatrabanka.masarykapp.repository.UsersRepository

class UserListViewModel : ViewModel() {
    private val usersRepository = UsersRepository.instance
    val usersObservable = usersRepository.usersObservable
    val usersExhaustedObservable = usersRepository.usersExhaustedObservable

    fun fetchUsers(page: Int, limit: Int) {
        usersRepository.fetchUsers(page, limit)
    }

    fun fetchUsersAndClearCache(page: Int, limit: Int) {
        usersRepository.fetchUsersAndClearCache(page, limit)
    }
}