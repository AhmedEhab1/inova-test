package com.example.inovatest




import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.inovatest.domain.DataModel
import com.example.inovatest.domain.DataRepository
import com.example.inovatest.viewModels.DataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DataViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockRepository: DataRepository

    @Mock
    private lateinit var mockDataObserver: Observer<DataModel?>

    @Mock
    private lateinit var mockErrorObserver: Observer<String?>

    private lateinit var viewModel: DataViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DataViewModel(mockRepository)
        viewModel.dataModel.observeForever(mockDataObserver)
        viewModel.errorMessage.observeForever(mockErrorObserver)
    }

    @Test
    fun `fetchData success`() = testDispatcher.runBlockingTest {
        val testDataModel = DataModel("Test Title", "Test Content") // Initialize with your test data
        `when`(mockRepository.fetchData()).thenReturn(testDataModel)

        viewModel.fetchData()

        verify(mockRepository).fetchData()
        verify(mockDataObserver).onChanged(testDataModel)
        verifyNoMoreInteractions(mockErrorObserver)
    }

    @Test
    fun `fetchData error`() = testDispatcher.runBlockingTest {
        val errorMessage = "Test error message"
        `when`(mockRepository.fetchData()).thenThrow(RuntimeException(errorMessage))

        viewModel.fetchData()

        verify(mockRepository).fetchData()
        verify(mockErrorObserver).onChanged(errorMessage)
        verifyNoMoreInteractions(mockDataObserver)
    }
}
