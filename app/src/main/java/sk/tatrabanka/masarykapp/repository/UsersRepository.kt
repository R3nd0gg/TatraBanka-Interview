package sk.tatrabanka.masarykapp.repository

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import sk.tatrabanka.masarykapp.model.User
import sk.tatrabanka.masarykapp.request.RestClient

class UsersRepository private constructor() {
    companion object {
        val instance = UsersRepository()
    }

    private val fetchedUsersList = mutableListOf<User>()
    val usersObservable = MediatorLiveData<List<User>>()
    val usersExhaustedObservable = MutableLiveData(false)
    private val restClient = RestClient.instance

    init {
        // initialize mediators
        this.usersObservable.addSource(restClient.usersObservable) {
            fetchedUsersList.addAll(it)
            usersObservable.postValue(fetchedUsersList)
            usersExhaustedObservable.postValue(it.isEmpty()) // fetching is exhausted when received list is empty
        }
    }

    fun fetchUsers(page: Int, limit: Int) {
        restClient.fetchUsers(page, limit)
    }

    fun fetchUsersAndClearCache(page: Int, limit: Int) {
        fetchedUsersList.clear()
        fetchUsers(page, limit)
    }
}