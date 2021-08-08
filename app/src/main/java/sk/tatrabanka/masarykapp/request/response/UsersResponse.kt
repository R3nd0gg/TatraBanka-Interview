package sk.tatrabanka.masarykapp.request.response

import com.google.gson.annotations.SerializedName
import sk.tatrabanka.masarykapp.model.User

data class UsersResponse(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val data: List<User>
)