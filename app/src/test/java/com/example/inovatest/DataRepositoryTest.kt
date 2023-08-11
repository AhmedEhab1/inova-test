package com.example.inovatest

import com.example.inovatest.domain.DataRepository
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.InputStream
import java.io.InputStreamReader

class DataRepositoryTest {

    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var mockInputStream: InputStream

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dataRepository = DataRepository()
    }

    @Test
    fun fetchData_ValidJson_ReturnsDataModel() {
        val json = """{"title": "Test Title", "content": "Test Content"}"""
        `when`(mockInputStream.bufferedReader().use { it.readText() }).thenReturn(json)

        val result = dataRepository.fetchData()

        assert(result.title == "Test Title")
        assert(result.content == "Test Content")
    }

    @Test(expected = Exception::class)
    fun fetchData_InvalidJson_ThrowsException() {
        val json = """invalid json"""
        `when`(mockInputStream.bufferedReader().use { it.readText() }).thenReturn(json)

        dataRepository.fetchData()
    }
}
