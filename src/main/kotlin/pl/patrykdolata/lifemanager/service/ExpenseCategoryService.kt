package pl.patrykdolata.lifemanager.service

import pl.patrykdolata.lifemanager.domain.ExpenseCategoryEntity
import pl.patrykdolata.lifemanager.model.ExpenseCategory

interface ExpenseCategoryService : CrudService<ExpenseCategory, ExpenseCategoryEntity, Long>
