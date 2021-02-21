package pl.patrykdolata.lifemanager.util

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Sort

class RequestUtilsTest {

    @Test
    fun `When String extension mapToSort maps only property string should return asc direction`() {
        val sort = "name"
        val result = sort.mapToSort()
        assertTrue(result.isAscending)
        assertThat(result.property, equalTo("name"))
    }

    @Test
    fun `When String extension mapToSort maps 'property desc' string should return descending sort object`() {
        val sort = "createdAt desc"
        val result = sort.mapToSort()
        assertTrue(result.isDescending)
        assertThat(result.property, equalTo("createdAt"))
    }

    @Test
    fun `When String extension mapToSort maps 'property asc' string should return ascending sort object`() {
        val sort = "createdAt asc"
        val result = sort.mapToSort()
        assertTrue(result.isAscending)
        assertThat(result.property, equalTo("createdAt"))
    }

    @Test
    fun `Given null string array of sort should return unsorted Sort`() {
        val sort: Array<String>? = null
        val result = createSort(sort)
        assertNotNull(result)
        assertThat(result, equalTo(Sort.unsorted()))
    }

    @Test
    fun `Given empty string array of sort should return unsorted Sort`() {
        val sort: Array<String> = emptyArray()
        val result = createSort(sort)
        assertNotNull(result)
        assertTrue(result.isUnsorted)
        assertTrue(result.isEmpty)
    }

    @Test
    fun `Given array of empty strings should return unsorted Sort`() {
        val sort: Array<String> = arrayOf("", "")
        val result = createSort(sort)
        assertNotNull(result)
        assertTrue(result.isUnsorted)
        assertTrue(result.isEmpty)
    }

    @Test
    fun `Given proper string array of sort should return proper Sort`() {
        val sort: Array<String> = arrayOf("createdAt desc", "name asc")
        val result = createSort(sort)
        assertNotNull(result)
        assertTrue(result.isSorted)
        assertFalse(result.isEmpty)
        assertTrue(result.getOrderFor("createdAt")!!.isDescending)
        assertTrue(result.getOrderFor("name")!!.isAscending)
    }

    @Test
    fun `Given null page index should return unpaged Pageable`() {
        val result = createSortedPageable(null, 25, null)
        assertTrue(result.isUnpaged)
    }

    @Test
    fun `Given null size of page should return Pageable with default page size`() {
        val result = createSortedPageable(1, null, arrayOf("name asc"))
        assertTrue(result.isPaged)
        assertThat(result.pageNumber, equalTo(1))
        assertThat(result.pageSize, equalTo(DEFAULT_PAGE_SIZE))
    }

    @Test
    fun `Given proper page info should return proper Pageable`() {
        val result = createSortedPageable(1, 50, arrayOf("name asc"))
        assertTrue(result.isPaged)
        assertThat(result.pageNumber, equalTo(1))
        assertThat(result.pageSize, equalTo(50))
    }
}
