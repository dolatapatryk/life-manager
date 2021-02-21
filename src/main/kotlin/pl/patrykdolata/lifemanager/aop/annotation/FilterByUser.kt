package pl.patrykdolata.lifemanager.aop.annotation

import org.springframework.stereotype.Component

@Component
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FilterByUser
