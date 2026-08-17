package com.example.connectivity_kit.data

import com.example.connectivity_kit.domain.SpeedTestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.system.measureTimeMillis

@Singleton
internal class SpeedTestEngine @Inject constructor() {

    suspend fun performSpeedTest(): SpeedTestResult = withContext(Dispatchers.IO) {
        val downloadMbps = try {
            measureDownloadSpeed()
        } catch (e: Exception) {
            0f
        }

        val uploadMbps = try {
            measureUploadSpeed()
        } catch (e: Exception) {
            0f
        }

        SpeedTestResult(downloadMbps = downloadMbps, uploadMbps = uploadMbps)
    }

    private suspend fun measureDownloadSpeed(): Float = coroutineScope {
        val url = "https://cachefly.cachefly.net/10mb.test"
        
        val deferreds = (1..2).map {
            async {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 3000
                connection.readTimeout = 3000
                
                var bytesRead = 0L
                val time = measureTimeMillis {
                    try {
                        connection.inputStream.use { input ->
                            val buffer = ByteArray(8 * 1024)
                            var read: Int
                            while (input.read(buffer).also { read = it } != -1 && bytesRead < 2 * 1024 * 1024) {
                                bytesRead += read
                            }
                        }
                    } catch (e: Exception) {
                        // Handle potential stream closed exceptions
                    }
                }
                connection.disconnect()
                Pair(bytesRead, time)
            }
        }
        
        val results = deferreds.awaitAll()
        val totalBytes = results.sumOf { it.first }
        val maxTimeMs = results.maxOf { it.second }.coerceAtLeast(1)
        
        (totalBytes * 8000f) / (maxTimeMs * 1000f)
    }

    private suspend fun measureUploadSpeed(): Float = coroutineScope {
        val url = "https://httpbin.org/post"
        val dummyData = ByteArray(1024 * 1024) { 0 }
        
        val deferreds = (1..2).map {
            async {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.connectTimeout = 3000
                connection.readTimeout = 3000
                connection.setRequestProperty("Content-Type", "application/octet-stream")
                
                var bytesWritten = 0L
                val time = measureTimeMillis {
                    try {
                        connection.outputStream.use { output ->
                            output.write(dummyData)
                            output.flush()
                            bytesWritten = dummyData.size.toLong()
                        }
                        connection.responseCode 
                    } catch (e: Exception) {
                        // Handle potential stream closed exceptions
                    }
                }
                connection.disconnect()
                Pair(bytesWritten, time)
            }
        }
        
        val results = deferreds.awaitAll()
        val totalBytes = results.sumOf { it.first }
        val maxTimeMs = results.maxOf { it.second }.coerceAtLeast(1)
        
        (totalBytes * 8000f) / (maxTimeMs * 1000f)
    }
}
