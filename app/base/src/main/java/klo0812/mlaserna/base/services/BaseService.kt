package klo0812.mlaserna.base.services

import okhttp3.OkHttpClient

/**
 * All services should have an HTTP client built using OkHttpClient.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-23
 */
open class BaseService(
    open val httpClient: OkHttpClient
)