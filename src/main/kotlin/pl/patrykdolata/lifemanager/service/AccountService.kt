package pl.patrykdolata.lifemanager.service

import pl.patrykdolata.lifemanager.model.NewAccount

interface AccountService {
    fun create(account: NewAccount): Long
}
