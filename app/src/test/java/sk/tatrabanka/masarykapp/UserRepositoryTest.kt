package sk.tatrabanka.masarykapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import org.junit.Rule
import org.junit.Test
import sk.tatrabanka.masarykapp.repository.UsersRepository

class UserRepositoryTest {
    @get:Rule
    val testRule = InstantTaskExecutorRule()
    private val repository = UsersRepository.instance

    @Test
    fun testUserFetch() {
        repository.fetchUsers(0, 5)
        repository.usersObservable.test()
            .awaitNextValue()
            .assertHasValue()
            .assertValue { it.size == 5 }
            .assertValue { it[0].firstName == "George" }
        repository.usersExhaustedObservable.test()
            .assertHasValue()
            .assertValue { it == false }
    }

    @Test
    fun testExhaustedUsers() {
        repository.fetchUsers(1000, 5)
        repository.usersObservable.test()
        repository.usersExhaustedObservable.test()
            .awaitNextValue()
            .assertHasValue()
            .assertValue { it == true }
    }
}