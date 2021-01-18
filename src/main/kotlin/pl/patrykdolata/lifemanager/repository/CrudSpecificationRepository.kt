package pl.patrykdolata.lifemanager.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface CrudSpecificationRepository<T, ID> : JpaRepository<T, ID>, JpaSpecificationExecutor<T>
