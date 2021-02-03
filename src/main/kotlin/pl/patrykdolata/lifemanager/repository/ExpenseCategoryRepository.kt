package pl.patrykdolata.lifemanager.repository

import org.springframework.stereotype.Repository
import pl.patrykdolata.lifemanager.domain.ExpenseCategoryEntity

@Repository
interface ExpenseCategoryRepository : CrudSpecificationRepository<ExpenseCategoryEntity, Long>
