package de.kramhal.sipgate.scli

import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl
import org.apache.xmlrpc.common.TypeFactoryImpl
import org.apache.xmlrpc.common.XmlRpcStreamConfig
import org.apache.xmlrpc.serializer.TypeSerializer
import org.apache.xmlrpc.serializer.TypeSerializerImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.xml.sax.SAXException
import java.net.URL

class SipgateRpcClient {
    companion object {
        val LOG = LoggerFactory.getLogger(SipgateScliAppRunner::class.java)
    }

    @Autowired
    var arguments: SipgateScliAppRunner.SipgateScliArguments = SipgateScliAppRunner.SipgateScliArguments()

    fun printArgs() = LOG.warn(arguments.toString())

    fun bla() {
        val config = XmlRpcClientConfigImpl()
        config.serverURL = URL("https://api.sipgate.net/RPC2")
        config.basicUserName = "test"
        config.basicPassword = "test"

        val client = getClient(config)

        val eventlistResult = getEventList(client)
        println(eventlistResult)

    }

    /**
     * returns client for sipgate API
     */
    private fun getClient(config: XmlRpcClientConfigImpl): XmlRpcClient {
        val client = XmlRpcClient()
        val sipgateTypeFactory = SipgateTypeFactory(client)
        client.typeFactory = sipgateTypeFactory
        client.setConfig(config)

        return client
    }

    /**
     * calls a API function
     */
    private fun callAPI(methodName: String, params: Map<String, Any>, client: XmlRpcClient): Any =
            client.execute(methodName, arrayOf<Any>(params))

    private fun click2Call(client: XmlRpcClient, source: String, toBeCalled: String) = callAPI("samurai.SessionInitiate",
            mapOf("LocalUri" to source, "RemoteUri" to toBeCalled, "TOS" to "X-call", "Content" to ""),
            client)

    /**
     * requests events for calls and voicemails
     */
    private fun getEventList(client: XmlRpcClient): Any =
            callAPI("samurai.EventListGet",
                    mapOf("Offset" to 0, "Limit" to 0, "TOS" to listOf("X-call", "voice")),
                    client)


    class SipgateTypeFactory(client: XmlRpcClient) : TypeFactoryImpl(client) {
        @Throws(SAXException::class)
        override fun getSerializer(pConfig: XmlRpcStreamConfig, pObject: Any): TypeSerializer =
                if (pObject is String) SipgateStringSerializer() else super.getSerializer(pConfig, pObject)
    }

    class SipgateStringSerializer : TypeSerializerImpl() {
        @Throws(SAXException::class)
        override fun write(handler: org.xml.sax.ContentHandler, `object`: Any) =
                write(handler, "string", "string", `object`.toString())
    }

    val status = mapOf(
            200 to "Method success",
            400 to "Method not supported",
            401 to "Request denied (no reason specified)",
            402 to "Internal error",
            403 to "Invalid arguments",
            404 to "Resources exceeded (this MUST not be used to indicate parameters in error)",
            405 to "Invalid parameter name",
            406 to "Invalid parameter type",
            407 to "Invalid parameter value",
            408 to "Attempt to set a non-writable parameter",
            409 to "Notification request rejected.",
            410 to "Parameter exceeds maximum size.",
            411 to "Missing parameter.",
            412 to "Too many requests.",
            500 to "Date out of range.",
            501 to "Uri does not belong to user.",
            502 to "Unknown type of service.",
            503 to "Selected payment method failed.",
            504 to "Selected currency not supported.",
            505 to "Amount exceeds limit.",
            506 to "Malformed SIP URI.",
            507 to "URI not in list.",
            508 to "Format is not valid E.164.",
            509 to "Unknown status.",
            510 to "Unknown ID.",
            511 to "Invalid timevalue.",
            512 to "Referenced session not found.",
            513 to "Only single default per TOS allowed.",
            514 to "Malformed VCARD format.",
            515 to "Malformed PID format.",
            516 to "Presence information not available.",
            517 to "Invalid label name.",
            518 to "Label not assigned.",
            519 to "Label doesn’t exist.",
            520 to "Parameter includes invalid characters.",
            521 to "Bad password. (Rejected due to security concerns.)",
            522 to "Malformed timezone format.",
            523 to "Delay exceeds limit.",
            524 to "Requested VPN type not available.",
            525 to "Requested TOS not available.",
            526 to "Unified messaging not available.",
            527 to "URI not available for registration.",
            900..999 to "Vendor defined status codes"
    )

}