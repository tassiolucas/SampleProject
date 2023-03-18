package com.example.sampleproject.domain.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<out T, in P> {
    protected abstract suspend fun run(params: P): Result<T>

    suspend operator fun invoke(params: P): Result<T> = withContext(Dispatchers.IO) {
        try {
            run(params)
        } catch (e: Throwable) {
            Result.failure<T>(e)
        }
    }
}

suspend operator fun <T> UseCase<T, Unit>.invoke(): Result<T> = this(Unit)