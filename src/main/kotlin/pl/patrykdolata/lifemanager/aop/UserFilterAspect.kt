package pl.patrykdolata.lifemanager.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.hibernate.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import pl.patrykdolata.lifemanager.domain.AbstractUserDependEntity.Companion.USER_FILTER
import pl.patrykdolata.lifemanager.domain.AbstractUserDependEntity.Companion.USER_ID_PARAMETER
import pl.patrykdolata.lifemanager.security.AuthenticationUtils
import javax.persistence.EntityManager

@Aspect
@Component
class UserFilterAspect(private val entityManager: EntityManager) {

    private val log: Logger = LoggerFactory.getLogger(UserFilterAspect::class.java)

    @Around("@annotation(pl.patrykdolata.lifemanager.aop.annotation.FilterByUser)")
    fun filterByUser(joinPoint: ProceedingJoinPoint): Any {
        val id = AuthenticationUtils.getCurrentUserId()
        log.debug("Enable FilterByUser to user: {}", id)
        val session: Session = getSession()
        enableFilter(id, session)
        val result = joinPoint.proceed()
        disableFilter(session)

        return result
    }

    private fun getSession() = entityManager.unwrap(Session::class.java)

    private fun enableFilter(userId: Long, session: Session) =
            session.enableFilter(USER_FILTER).setParameter(USER_ID_PARAMETER, userId)

    private fun disableFilter(session: Session) = session.disableFilter(USER_FILTER)
}
