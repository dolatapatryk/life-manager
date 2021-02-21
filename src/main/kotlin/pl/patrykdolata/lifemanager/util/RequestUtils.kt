package pl.patrykdolata.lifemanager.util

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification

const val DEFAULT_PAGE_SIZE = 25

fun createSortedPageable(page: Int?, size: Int?, sort: Array<String>?): Pageable {
    return createPageable(page, size, createSort(sort))
}

private fun createPageable(page: Int?, size: Int?, sort: Sort = Sort.unsorted()): Pageable {
    return if (page != null) {
        PageRequest.of(page, size ?: DEFAULT_PAGE_SIZE, sort)
    } else {
        Pageable.unpaged()
    }
}

fun createSort(sort: Array<String>?): Sort {
    return if (!sort.isNullOrEmpty()) {
        mapSort(sort)
    } else {
        Sort.unsorted()
    }
}

private fun mapSort(sort: Array<String>): Sort {
    return Sort.by(sort.filter { it.isNotEmpty() }.map { it.mapToSort() })
}

fun <T> createSpecification(search: Array<String>?): Specification<T> {
    return Specification.where(null)
}

internal fun String.mapToSort(): Sort.Order {
    val split = this.split(" ")
    val direction = if (split.size > 1 && split[1].equals("desc", ignoreCase = true))
        Sort.Direction.DESC else Sort.Direction.ASC
    return Sort.Order(direction, split[0])
}
