package sk.tatrabanka.masarykapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sk.tatrabanka.masarykapp.model.User
import sk.tatrabanka.masarykapp.repository.UserDetailsRepository

class UserDetailsViewModel : ViewModel() {
    var userId = -1
    private val usersRepository = UserDetailsRepository.instance

    fun fetchUserDetails(): LiveData<User> {
        return usersRepository.fetchUserDetails(userId)
    }

    fun reFetchUserDetails() {
        usersRepository.fetchUserDetails(userId)
    }
}