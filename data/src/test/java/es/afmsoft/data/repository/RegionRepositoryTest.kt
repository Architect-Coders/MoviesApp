package es.afmsoft.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import es.afmsoft.data.sources.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    lateinit var regionRepository: RegionRepository

    @Before
    fun setUp() {
        regionRepository = RegionRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `findLastRegion gets last region no data`() {
        runBlocking {
            whenever(permissionChecker.check(any())).thenReturn(true)
            whenever(locationDataSource.findLastRegion()).thenReturn(null)

            val result = regionRepository.findLastRegion()

            assertEquals("US", result)
        }
    }

    @Test
    fun `findLastRegion gets last region`() {
        runBlocking {
            whenever(permissionChecker.check(any())).thenReturn(true)
            whenever(locationDataSource.findLastRegion()).thenReturn("ES")

            val result = regionRepository.findLastRegion()

            assertEquals("ES", result)
        }
    }

    @Test
    fun `findLastRegion gets default region because no permission`() {
        runBlocking {
            whenever(permissionChecker.check(any())).thenReturn(false)

            val result = regionRepository.findLastRegion()

            assertEquals("US", result)
        }
    }
}
