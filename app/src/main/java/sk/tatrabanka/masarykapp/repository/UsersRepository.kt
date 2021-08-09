package sk.tatrabanka.masarykapp.repository

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import sk.tatrabanka.masarykapp.model.User
import sk.tatrabanka.masarykapp.request.RestClient

class UsersRepository private constructor() {
    companion object {
        val instance = UsersRepository()
        const val FETCH_LIMIT = 10
    }

    private val fetchedUsersList = mutableListOf<User>()
    private val restClient = RestClient.instance
    private var nextPage = 1
    val usersObservable = MediatorLiveData<List<User>>()
    val usersExhaustedObservable = MutableLiveData(false)

    init {
        // initialize mediators
        this.usersObservable.addSource(restClient.usersObservable) {
            fetchedUsersList.addAll(it)
            usersObservable.postValue(fetchedUsersList)
            usersExhaustedObservable.postValue(it.isEmpty()) // fetching is exhausted when received list is empty
        }
        // fetch first data
        if (fetchedUsersList.isEmpty()) {
            fetchNextPage()
        }
    }

    fun fetchNextPage() {
        restClient.fetchUsers(nextPage, FETCH_LIMIT)
        nextPage++
    }

    fun clearCacheAndFetch() {
        nextPage = 1
        fetchedUsersList.clear()
        fetchNextPage()
    }
}