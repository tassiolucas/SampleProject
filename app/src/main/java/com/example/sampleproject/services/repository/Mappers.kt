package com.example.sampleproject.services.repository

class Mappers(private vararg val mappers: Mapper<*, *>) {

    fun <TSource : Any, TTarget : Any> mapTo(source: TSource): TTarget {
        mappers.forEach { mapper ->
            mapper.mapTo(this, source)?.let {
                return it as TTarget
            }
        }

        throw Throwable("Mapper not found")
    }
}

fun <TSource : Any, TTarget : Any> TSource.mapTo(mappers: Mappers): TTarget = mappers.mapTo(this)