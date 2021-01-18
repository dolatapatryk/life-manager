package pl.patrykdolata.lifemanager.controller

import org.slf4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.patrykdolata.lifemanager.model.IdResponse
import pl.patrykdolata.lifemanager.service.CrudService
import pl.patrykdolata.lifemanager.util.RequestUtils.createSort
import pl.patrykdolata.lifemanager.util.RequestUtils.createSortedPageable
import pl.patrykdolata.lifemanager.util.RequestUtils.createSpecification
import pl.patrykdolata.lifemanager.util.ResponseUtils.createdResponse
import pl.patrykdolata.lifemanager.util.ResponseUtils.pageResponse
import pl.patrykdolata.lifemanager.util.ResponseUtils.response

abstract class AbstractCrudController<M, E, ID>(private val service: CrudService<M, E, ID>) {

    abstract fun getLogger(): Logger

    abstract fun getModelClassName(): String?

    @GetMapping
    fun findAll(
            @RequestParam(name = "page", required = false) page: Int?,
            @RequestParam(name = "size", required = false) size: Int?,
            @RequestParam(name = "search", required = false) search: Array<String>?,
            @RequestParam(name = "sort", required = false) sort: Array<String>?
    ): ResponseEntity<List<M>> {
        getLogger().debug("Request to get user's ${getModelClassName()}s")
        val pageable = createSortedPageable(page, size, sort)
        val specification = createSpecification<E>(search)
        return if (pageable.isPaged) {
            pageResponse(service.findAll(specification, pageable))
        } else {
            response(service.findAll(specification, createSort(sort)))
        }
    }

    @GetMapping("/{id}")
    fun findOne(
            @PathVariable(name = "id", required = true) id: ID
    ): ResponseEntity<M> {
        getLogger().debug("Request to get user's ${getModelClassName()} by id: $id")
        val model: M = service.findOne(id)

        return response(model)
    }

    @PostMapping
    fun create(@RequestBody model: M): ResponseEntity<IdResponse<ID>> {
        getLogger().debug("Request to add new ${getModelClassName()}")
        val newId = service.create(model)

        return createdResponse(IdResponse(newId))
    }
}
