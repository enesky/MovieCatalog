package dev.enesky.core.network

import dev.enesky.core.common.data.base.AuthorizationException
import dev.enesky.core.common.data.base.BadRequestException
import dev.enesky.core.common.data.base.EmptyResponseException
import dev.enesky.core.common.data.base.ExceptionCode
import dev.enesky.core.common.data.base.NetworkException
import dev.enesky.core.common.data.base.NotFoundException
import dev.enesky.core.common.data.Resource
import dev.enesky.core.common.data.base.UnknownException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiToBeCalled()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                Resource.Success(body)
            } else {
                Resource.Error(EmptyResponseException())
            }
        } catch (e: HttpException) {
            val message = try {
                Json.parseToJsonElement(
                    e.response()?.errorBody()?.string().orEmpty()
                ).jsonObject["message"]?.jsonPrimitive?.content
            } catch (e: Exception) {
                null
            }

            when (e.code()) {
                ExceptionCode.BAD_REQUEST -> Resource.Error(BadRequestException(message))
                ExceptionCode.AUTHORIZATION -> Resource.Error(AuthorizationException(message))
                ExceptionCode.NOT_FOUND -> Resource.Error(NotFoundException(message))
                else -> Resource.Error(UnknownException(message))
            }
        } catch (e: IOException) {
            Resource.Error(NetworkException(e.message))
        } catch (e: Exception) {
            Resource.Error(UnknownException(e.message))
        }
    }
}
