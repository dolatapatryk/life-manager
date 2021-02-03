package pl.patrykdolata.lifemanager.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pl.patrykdolata.lifemanager.domain.ExpenseCategoryEntity
import pl.patrykdolata.lifemanager.mapper.ExpenseCategoryMapper
import pl.patrykdolata.lifemanager.model.ExpenseCategory
import pl.patrykdolata.lifemanager.repository.ExpenseCategoryRepository
import pl.patrykdolata.lifemanager.service.ExpenseCategoryService

@Service
class ExpenseCategoryServiceImpl(expenseCategoryRepository: ExpenseCategoryRepository, expenseCategoryMapper: ExpenseCategoryMapper) : CrudServiceImpl<ExpenseCategory, ExpenseCategoryEntity, Long>(expenseCategoryRepository, expenseCategoryMapper), ExpenseCategoryService {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ExpenseCategoryServiceImpl::class.java)
    }

    override fun getLogger(): Logger = logger

    override fun getModelClassName(): String? = ExpenseCategory::class.simpleName
}
