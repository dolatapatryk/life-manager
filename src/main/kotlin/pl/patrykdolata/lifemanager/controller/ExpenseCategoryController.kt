package pl.patrykdolata.lifemanager.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.patrykdolata.lifemanager.domain.ExpenseCategoryEntity
import pl.patrykdolata.lifemanager.model.ExpenseCategory
import pl.patrykdolata.lifemanager.service.ExpenseCategoryService

@RestController
@RequestMapping("/api/expenses/categories")
class ExpenseCategoryController(expenseCategoryService: ExpenseCategoryService)
    : AbstractCrudController<ExpenseCategory, ExpenseCategoryEntity, Long>(expenseCategoryService) {

        companion object {
            private val logger: Logger = LoggerFactory.getLogger(ExpenseCategoryController::class.java)
        }

    override fun getLogger(): Logger = logger

    override fun getModelClassName(): String? = ExpenseCategoryController::class.simpleName
}
