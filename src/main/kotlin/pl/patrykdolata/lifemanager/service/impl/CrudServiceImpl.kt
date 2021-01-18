package pl.patrykdolata.lifemanager.service.impl

import org.slf4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import pl.patrykdolata.lifemanager.aop.annotation.FilterByUser
import pl.patrykdolata.lifemanager.mapper.EntityMapper
import pl.patrykdolata.lifemanager.repository.CrudSpecificationRepository
import pl.patrykdolata.lifemanager.service.CrudService

abstract class CrudServiceImpl<M, E, ID>(
        private val repository: CrudSpecificationRepository<E, ID>,
        private val mapper: EntityMapper<M, E>
) : CrudService<M, E, ID> {

    abstract fun getLogger(): Logger

    abstract fun getModelClassName(): String?

    @FilterByUser
    override fun findAll(specification: Specification<E>, pageable: Pageable): Page<M> {
        getLogger().debug("Find page of user's ${getModelClassName()}")
        val page = repository.findAll(specification, pageable)

        return page.map { mapper.toModel(it) }
    }

    @FilterByUser
    override fun findAll(specification: Specification<E>, sort: Sort): List<M> {
        getLogger().debug("Find all user's ${getModelClassName()}")
        val list = repository.findAll(specification, sort)

        return mapper.toModel(list)
    }

    @FilterByUser
    override fun findOne(id: ID): M {
        TODO("Not yet implemented")
    }

    override fun create(model: M): ID {
        TODO("Not yet implemented")
    }

    @FilterByUser
    override fun update(id: ID, model: M) {
        TODO("Not yet implemented")
    }

    @FilterByUser
    override fun delete(id: ID) {
        TODO("Not yet implemented")
    }
}
