package com.example.moviesappdemo.api

import com.example.moviesappdemo.BuildConfig
import com.example.moviesappdemo.datasource.network.api.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {

    private lateinit var service: ApiService
    private lateinit var mockWebServer: MockWebServer
    private val apiKey = BuildConfig.API_KEY
    private val MOVIES_BASE_URL = "https://api.themoviedb.org/"

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(MOVIES_BASE_URL))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addConverterFactory(provideGsonConverterFactory(Gson()))
            .build()
            .create(ApiService::class.java)
    }

    private fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)


    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestLatestMovies() {
        runBlocking {
            val resultResponse =
                service.getLatestMovies(apiKey = apiKey, language = "en-US", page = 1).await()
            val request = mockWebServer.takeRequest()

            assertNotNull(resultResponse)
            assertThat(request.path, `is`("3/movie/now_playing"))
        }
    }

    @Test
    fun requestUpcomingMovies() {
        runBlocking {
            val resultResponse =
                service.getUpcomingMovies(apiKey = apiKey, language = "en-US", page = 1).await()
            val request = mockWebServer.takeRequest()

            assertNotNull(resultResponse)
            assertThat(request.path, `is`("3/movie/upcoming"))
        }
    }

    @Test
    fun requestPopularMovies() {
        runBlocking {
            val resultResponse =
                service.getPopularMovies(apiKey = apiKey, language = "en-US", page = 1).await()
            val request = mockWebServer.takeRequest()

            assertNotNull(resultResponse)
            assertThat(request.path, `is`("3/movie/popular"))
        }
    }

}


