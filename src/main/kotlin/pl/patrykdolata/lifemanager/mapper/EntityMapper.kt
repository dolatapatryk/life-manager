package pl.patrykdolata.lifemanager.mapper

import org.mapstruct.IterableMapping
import org.mapstruct.Named

interface EntityMapper<M, E> {

    @Named("toModel")
    fun toModel(entity: E): M

    @IterableMapping(qualifiedByName = ["toModel"])
    fun toModel(entities: Iterable<E>): List<M>

    fun toEntity(model: M): E
}
