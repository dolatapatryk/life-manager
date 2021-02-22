package pl.patrykdolata.lifemanager.model

import java.math.BigDecimal

data class Account(val id: Long?, val name: String, val balance: BigDecimal) {
    constructor(name: String, balance: BigDecimal): this(null, name, balance)
}
