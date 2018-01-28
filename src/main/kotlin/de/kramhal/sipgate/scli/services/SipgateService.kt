package de.kramhal.sipgate.scli.services

/**
 * Standard-Interface for a Sipgate-Service. `T` is the Bean-Representation of the Answer.
 */
interface SipgateService<out T : SipgateServiceResult> {
    val methodName: String
    val params: Map<String, Any>

    /** converts the result into a Bean */
    fun convert(result: Any): T
}