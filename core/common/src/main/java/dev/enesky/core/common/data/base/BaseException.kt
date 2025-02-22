package dev.enesky.core.common.data.base

open class BaseException(message: String) : Exception(message)

class EmptyResponseException(message: String? = null) : BaseException(
    message ?: ExceptionMessage.UNKNOWN
)

class BadRequestException(message: String? = null) : BaseException(
    message ?: ExceptionMessage.BAD_REQUEST
)

class AuthorizationException(message: String? = null) : BaseException(
    message ?: ExceptionMessage.AUTHORIZATION
)

class NotFoundException(message: String? = null) : BaseException(
    message ?: ExceptionMessage.NOT_FOUND
)

class UnknownException(message: String? = null) : BaseException(
    message ?: ExceptionMessage.UNKNOWN
)

class NetworkException(message: String? = null) : BaseException(
    message ?: ExceptionMessage.NETWORK
)

object ExceptionMessage {
    const val EMPTY_RESPONSE = "The response is empty."
    const val BAD_REQUEST = "The request could not be understood by the server due to malformed syntax."
    const val AUTHORIZATION = "You are not authorized to access this resource."
    const val NOT_FOUND = "The requested resource could not be found."
    const val UNKNOWN = "An unknown error occurred, please try again later."
    const val NETWORK = "Please check your internet connection."
}

object ExceptionCode {
    const val EMPTY_RESPONSE = 0
    const val BAD_REQUEST = 400
    const val AUTHORIZATION = 401
    const val NOT_FOUND = 404
    const val UNKNOWN = 500
    const val NETWORK = 600
}
