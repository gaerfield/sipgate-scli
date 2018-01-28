package de.kramhal.sipgate.scli.services

/**
 * Standard-Service-Answer for an Sipgate-Service - not sure if its really needed because apache-api seems to fail
 * when the answer does not contain 200=OK
 */
interface SipgateServiceResult {
    data class Status(val code: Int, val status: String)

    /** Status returned by an Sipgate-Service */
    val status: Status

    fun buildStatus(requestResult: Any): Status {
        val map = requestResult as Map<String, Any>
        val theCode = map.get("StatusCode") as Int
        val theMessage = map.get("StatusString") as String
        return Status(theCode, theMessage)
    }
}