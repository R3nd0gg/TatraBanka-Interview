package sk.tatrabanka.masarykapp.repository

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import sk.tatrabanka.masarykapp.model.User
import sk.tatrabanka.masarykapp.request.RestClient

class UsersRepository private constructor() {
    companion object {
        val instance = UsersRepository()
    }

    val usersObservable = MediatorLiveData<List<User>>()
    val usersExhaustedObservable = MutableLiveData(false)
    private val restClient = RestClient.instance

    init {
        // initialize mediators
        this.usersObservable.addSource(restClient.usersObservable) {
            if (it.isNotEmpty()) {
                usersObservable.postValue(it)
            }
            // fetching is exhausted when received list is empty
            usersExhaustedObservable.postValue(it.isEmpty())
        }
    }

    fun fetchUsers(page: Int, limit: Int) {
        restClient.fetchUsers(page, limit)
    }
}