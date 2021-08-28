package com.countries.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.countries.weather.api.ApiClient
import com.countries.weather.api.ApiService
import com.countries.weather.api.model.WCLocation
import com.countries.weather.viewmodels.CitySearchViewModel

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONArray
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class CitySearchViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var cityViewModel: CitySearchViewModel

    private lateinit var apiService: ApiService

    @Mock
    private lateinit var liveData: Observer<List<WCLocation?>>

    private lateinit var webServer: MockWebServer


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        cityViewModel = CitySearchViewModel()
        cityViewModel.getCities().observeForever(liveData)

        webServer = MockWebServer()
        webServer.start()
        apiService = ApiClient.instance
    }

    @Test
    fun `testing for to read the json response`(){
        val reader = MockFileReader("success_response.json")
        assertNotNull(reader.body)
    }

    @Test
    fun `testing for api call repsonse 200 `(){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockFileReader("success_response.json").body)
        webServer.enqueue(response)

        runBlocking {
            val  actualResponse = apiService.searchLocation("san")
            // Assert
            assertEquals(response.toString().contains("200"),actualResponse.code().toString().contains("200"))

        }
    }

    @Test
    fun `testing for api call return success result`(){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockFileReader("success_response.json").body)
        webServer.enqueue(response)
        val mockResponse = response.getBody()?.readUtf8()
        print(mockResponse)

        runBlocking {
            val  actualResponse = apiService.searchLocation("san").body()

            // Assert
            assertEquals(mockResponse?.let { `testing for json response`(it) }, actualResponse)

        }
    }

    private fun `testing for json response`(mockResponse: String): JSONArray {
        val reader = JSONObject(mockResponse)
        return reader.getJSONArray("data")
    }

    @Test
    fun `testing for response 400 returned`(){
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(MockFileReader("failed_response.json").body)
        webServer.enqueue(response)

        runBlocking {
            // Act
            val  actualResponse = apiService.searchLocation("san")
            // Assert
            assertEquals(response.toString().contains("400"),actualResponse.toString().contains("400"))

        }
    }

    @After
    fun tearDown() {
        cityViewModel.getCities().removeObserver(liveData)
        webServer.shutdown()
    }
}