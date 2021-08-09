package sk.tatrabanka.masarykapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import sk.tatrabanka.masarykapp.model.User
import sk.tatrabanka.masarykapp.request.RestClient

class UserDetailsRepository {
    companion object {
        val instance = UserDetailsRepository()
    }

    private val restClient = RestClient.instance

    fun fetchUserDetails(id: Int): LiveData<User> {
        val idFilterLiveData = MediatorLiveData<User>()
        // this livedata filters ids of other users which we don't need
        idFilterLiveData.addSource(restClient.userDetailsObservable) {
            if (it.id == id) {
                idFilterLiveData.postValue(it)
            }
        }
        restClient.fetchUserDetails(id)
        return idFilterLiveData
    }

    fun reFetchUserDetails(id: Int) {
        restClient.fetchUserDetails(id)
    }
}