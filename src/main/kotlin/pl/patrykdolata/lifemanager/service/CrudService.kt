package pl.patrykdolata.lifemanager.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification

interface CrudService<M, E, ID> {
    fun findAll(specification: Specification<E>, pageable: Pageable): Page<M>

    fun findAll(specification: Specification<E>, sort: Sort): List<M>

    fun findOne(id: ID): M

    fun create(model: M): ID

    fun update(id: ID, model: M)

    fun delete(id: ID)
}
