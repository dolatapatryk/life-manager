package pl.patrykdolata.lifemanager.service

import pl.patrykdolata.lifemanager.model.LoginInfo
import pl.patrykdolata.lifemanager.model.NewUser
import pl.patrykdolata.lifemanager.model.User

interface UserService {
    fun authorize(loginInfo: LoginInfo): User

    fun register(user: NewUser)
}
