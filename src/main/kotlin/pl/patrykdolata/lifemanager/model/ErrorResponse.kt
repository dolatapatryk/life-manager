package pl.patrykdolata.lifemanager.model

data class ErrorResponse(val message: String, val status: Int, val timestamp: Long, val path: String)
