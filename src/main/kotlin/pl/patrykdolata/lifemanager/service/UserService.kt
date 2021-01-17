package pl.patrykdolata.lifemanager.service

import pl.patrykdolata.lifemanager.model.JwtToken
import pl.patrykdolata.lifemanager.model.LoginInfo
import pl.patrykdolata.lifemanager.model.NewUser

interface UserService {
    fun authorize(loginInfo: LoginInfo): JwtToken

    fun register(user: NewUser)
}
