package sk.tatrabanka.masarykapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import org.junit.Rule
import org.junit.Test
import sk.tatrabanka.masarykapp.request.RestClient

class RestClientTest {
    @get:Rule
    val testRule = InstantTaskExecutorRule()
    private val restClient = RestClient.instance

    @Test
    fun testUserFetch() {
        restClient.fetchUsers(0, 5)
        restClient.usersObservable.test()
            .awaitNextValue()
            .assertHasValue()
            .assertValue { it.size == 5 }
            .assertValue { it[0].firstName == "George" }
    }

    @Test
    fun testEmptyUserFetch() {
        restClient.fetchUsers(1000, 5)
        restClient.usersObservable.test()
            .awaitNextValue()
            .assertHasValue()
            .assertValue { it.isEmpty() }
    }
}