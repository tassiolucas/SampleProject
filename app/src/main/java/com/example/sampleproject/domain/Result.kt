package com.example.sampleproject.domain

open class Result<out R> @PublishedApi internal constructor(
    @PublishedApi
    internal val value: Any? = null
) {

    val isSuccess: Boolean = value !is Throwable
    val isFailure: Boolean = value is Throwable

    fun getOrNull(): R? = when {
        isFailure -> null
        else -> value as R
    }

    fun exceptionOrNull(): Throwable? = when {
        isFailure -> value as Throwable?
        else -> null
    }

    companion object {
        fun <T> success(value: T): Result<T> =
            Result(value)
        fun <T> failure(throwable: Throwable): Result<T> =
            Result(throwable)
    }
}

inline fun <T> result(block: () -> T): Result<T> = try {
    Result.success(block())
} catch (e: Throwable) {
    Result.failure(e)
}

inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
    if (isSuccess) block(value as T)
    return this
}

inline fun <T> Result<T>.onFailure(block: (Throwable) -> Unit): Result<T> {
    exceptionOrNull()?.let { block(it) }
    return this
}

inline fun <T, R> Result<T>.map(transform: (value: T) -> R): Result<R> = when {
    isSuccess -> result { transform(this.value as T) }
    else -> Result(value)
}