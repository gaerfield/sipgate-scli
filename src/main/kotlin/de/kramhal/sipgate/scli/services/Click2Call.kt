package de.kramhal.sipgate.scli.services

/**
 * Initiates a new call (but fails with "Invalid Parameter" for whatever reason)
 */
class Click2Call(val home: String, val target: String) : SipgateService<Click2Call.CallResult> {
    override val methodName: String
        get() = "samurai.SessionInitiate"
    override val params: Map<String, Any>
        get() = mapOf("LocalUri" to home
                , "RemoteUri" to target
                , "TOS" to "X-call"
                , "Content" to ""
        )

    data class CallResult(val result: Any) : SipgateServiceResult {
        override val status: SipgateServiceResult.Status
            get() = super.buildStatus(result)
    }

    override fun convert(result: Any) = CallResult(result)
}