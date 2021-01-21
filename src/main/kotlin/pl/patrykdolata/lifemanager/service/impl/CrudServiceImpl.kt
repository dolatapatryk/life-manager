package pl.patrykdolata.lifemanager.service.impl

import org.slf4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import pl.patrykdolata.lifemanager.aop.annotation.FilterByUser
import pl.patrykdolata.lifemanager.domain.AbstractEntity
import pl.patrykdolata.lifemanager.mapper.EntityMapper
import pl.patrykdolata.lifemanager.repository.CrudSpecificationRepository
import pl.patrykdolata.lifemanager.service.CrudService
import java.io.Serializable
import javax.persistence.EntityNotFoundException

abstract class CrudServiceImpl<M, E : AbstractEntity<ID>, ID : Serializable>(
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
        getLogger().debug("Find ${getModelClassName()} by id: $id")
        val entity = repository.findOneById(id)

        return if (entity != null) mapper.toModel(entity) else
            throw EntityNotFoundException(getExceptionMessage(id))
    }

    override fun create(model: M): ID {
        getLogger().debug("Create new ${getModelClassName()}")
        val newEntity: E = repository.save(mapper.toEntity(model))

        return newEntity.id!!
    }

    @FilterByUser
    override fun update(id: ID, model: M) {
        getLogger().debug("Update ${getModelClassName()} with id: $id")
        val existing = repository.findOneById(id)
        if (existing != null) updateEntity(id, model) else throw EntityNotFoundException(getExceptionMessage(id))
    }

    @FilterByUser
    override fun delete(id: ID) {
        getLogger().debug("Delete ${getModelClassName()} with id: $id")
        val existing = repository.findOneById(id)
        if (existing != null) repository.deleteById(id) else throw EntityNotFoundException(getExceptionMessage(id))
    }

    private fun updateEntity(id: ID, model: M) {
        val toUpdate = mapper.toEntity(model)
        toUpdate.id = id
        repository.save(toUpdate)
    }

    private fun getExceptionMessage(id: ID): String = "${getModelClassName()} with id: $id not found"
}
