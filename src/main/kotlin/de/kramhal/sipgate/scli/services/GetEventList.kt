package de.kramhal.sipgate.scli.services

/**
 * get all stored voice-mail and click2call-events
 */
class GetEventList : SipgateService<GetEventList.EventList> {
    override val methodName: String
        get() = "samurai.EventListGet"
    override val params: Map<String, Any>
        get() = mapOf("Offset" to 0, "Limit" to 0, "TOS" to listOf("X-call", "voice"))

    data class Event(val source: String, val target: String, val type: String, val time: String)
    data class EventList(val result: Any) : SipgateServiceResult {
        override val status: SipgateServiceResult.Status
            get() = super.buildStatus(result)
        val events: List<Event>
            get() = ((result as Map<String, Any>).get("EventList") as Array<Any>)
                    .map { m -> m as Map<String, Any> }
                    .map { m ->
                        Event(
                                m.get("SourceUri") as String,
                                m.get("TargetUri") as String,
                                m.get("TOS") as String,
                                m.get("Timestamp") as String
                        )
                    }.toList()
    }

    override fun convert(result: Any): EventList = EventList(result)
}