package sk.tatrabanka.masarykapp.request

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import sk.tatrabanka.masarykapp.model.User
import sk.tatrabanka.masarykapp.request.response.UsersResponse
import java.io.IOException
import java.util.concurrent.TimeUnit

class RestClient private constructor() {
    companion object {
        private const val API_ENDPOINT = "https://reqres.in/api/users"
        private const val API_QUERY_PAGE = "page"
        private const val API_QUERY_LIMIT = "per_page"

        val instance = RestClient()
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    val usersObservable = MutableLiveData<List<User>>()

    fun fetchUsers(page: Int, limit: Int) {
        // create request URL
        val url = API_ENDPOINT.toHttpUrl().newBuilder()
        url.addQueryParameter(API_QUERY_PAGE, page.toString())
        url.addQueryParameter(API_QUERY_LIMIT, limit.toString())

        val request = Request.Builder()
            .url(url.build())
            .build()
        // execute request
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) TODO("Not yet implemented")
                    // parse response and post to livedata
                    val parsedResponse =
                        Gson().fromJson(response.body?.charStream(), UsersResponse::class.java)
                    usersObservable.postValue(parsedResponse.data)
                }
            }
        })
    }
}