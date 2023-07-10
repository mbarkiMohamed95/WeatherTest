package com.example.data.utils

import com.example.data.base.DomainDTOMappingService
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.CancellationException


suspend inline fun <R : Any> Result.Companion.runCatching(
    block: suspend () -> Result<R>
): Result<R> = try {
    val result = block().getOrThrow()
    success(result)
} catch (e: CancellationException) {
    throw e
}
suspend fun <R : Any, T : Any> Result.Companion.runCatchingAndMapToDomain(
    mapper: DomainDTOMappingService<R, T>,
    block: suspend () -> Result<R>
): Result<T> =
    try {
        val result = runCatching { block() }
        success(mapper.mapInputToOutput(result.getOrThrow()))
    } catch (e: Exception) {
        failure(e)
    }

suspend fun <R : Any, T : Any> Result.Companion.runCatchingAndMapToListDomain(
    mapper: DomainDTOMappingService<R, T>,
    block: suspend () -> Result<List<R>>
): Result<List<T>> =
    try {
        val result = runCatching { block() }
        success(mapper.mapInputToOutput(result.getOrThrow()))
    } catch (e: Exception) {
        failure(e)
    }

suspend inline fun <R> Result.Companion.runCatchingResponse(
    block: suspend () -> Response<R>
): Result<R> {
    return try {
        val response = block()
        if (response.isSuccessful) success(response.body()!!)
        else throw HttpException(response)
    } catch (e: Exception) {
        failure(e)
    }
}