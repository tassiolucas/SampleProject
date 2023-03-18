package com.example.sampleproject.services.repository

import android.util.Log
import kotlin.reflect.KClass

class Mapper<TSource: Any, TTarget: Any>(
    private val sourceClass: KClass<TSource>,
    private val map: TSource.(Mappers) -> TTarget
) {

    fun mapTo(mappers: Mappers, source: Any): TTarget? {
        return if (sourceClass.isInstance(source)) {
            try {
                (source as TSource).map(mappers)
            } catch (cause: Throwable) {
                throw InvalidMappingException(
                    source = source,
                    mapFunc = map,
                    cause = cause
                )
            }
        } else null
    }
}

inline fun <reified TSource: Any, reified TTarget: Any> mapper(noinline map: TSource.(Mappers) -> TTarget) = Mapper(TSource::class, map)

class InvalidMappingException(
    source: Any,
    mapFunc: Any,
    cause: Throwable
) : Throwable(cause) {
    init {
        buildString {
            val failureLocation = cause.stackTrace.firstOrNull { it.methodName.contains("invoke") }

            append(
                "Object mapping failure: \n",
                "\tSource Class: ${source::class.simpleName}\n",
                "\tSource Values: $source\n",
                "\tMappers: $mapFunc\n",
                "\tCause: ${cause.message}\n",
                "\tAt: ${failureLocation?.className}:${failureLocation?.lineNumber}\n"
            )
        }.also {
            Log.e(InvalidMappingException::class.simpleName, it)
        }
    }
}