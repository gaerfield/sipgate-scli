package de.kramhal.sipgate.scli

import de.kramhal.sipgate.scli.services.*
import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl
import org.apache.xmlrpc.common.TypeFactoryImpl
import org.apache.xmlrpc.common.XmlRpcStreamConfig
import org.apache.xmlrpc.serializer.TypeSerializer
import org.apache.xmlrpc.serializer.TypeSerializerImpl
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.xml.sax.SAXException
import java.net.URL

/**
 * XMLRPC-Wrapper for Sipgate. It uses `SipgateService` and `SipgateServiceResult` to call Sipgate-Services
 */
@Component
class SipgateRpcClient(val arguments: SipgateScliArguments) {
    companion object {
        val LOG = LoggerFactory.getLogger(SipgateRpcClient::class.java)!!
    }

    private final val client = XmlRpcClient()
    private fun <T : SipgateServiceResult> XmlRpcClient.callService(api: SipgateService<T>) =
            api.convert(this.execute(api.methodName, kotlin.arrayOf(api.params)))

    init {
        LOG.info(arguments.toString())
        val rpcConfig = XmlRpcClientConfigImpl()
        rpcConfig.serverURL = URL("https://api.sipgate.net/RPC2")
        rpcConfig.basicUserName = arguments.user
        rpcConfig.basicPassword = arguments.password

        val sipgateTypeFactory = SipgateTypeFactory(client)
        client.typeFactory = sipgateTypeFactory
        client.setConfig(rpcConfig)
    }

    /** Call the number given in the target-argument */
    fun initiateCall() {
        //val result = client.callService(GetEventList())
        val result = client.callService(Click2Call(arguments.home, arguments.target))
        LOG.info(result.status.toString())
        //LOG.info(result.events.joinToString("\n"))
    }

    /**
     * Logs all available info about the server, its services, comments on services and service-parameters (don't
     * expect much, nearly no info is provided).
     */
    fun logServerInfos() {
        LOG.info(client.callService(GetServerInfo()).serverInfo.toString())
        val services = client.callService(GetMethods()).methods
        LOG.info(services.joinToString("\n"))
        services.forEach { m ->
            LOG.info(m)
            LOG.info(client.callService(GetMethodHelp(m)).message.toString())
            val serviceResult = client.callService(GetMethodSignature(m))
            val signaturesOfMethod = serviceResult.signatures
            LOG.info(signaturesOfMethod.joinToString("\n"))
        }
    }


    class SipgateTypeFactory(client: XmlRpcClient) : TypeFactoryImpl(client) {
        @Throws(SAXException::class)
        override fun getSerializer(pConfig: XmlRpcStreamConfig, pObject: Any): TypeSerializer =
                if (pObject is String)
                    SipgateStringSerializer()
                else
                    super.getSerializer(pConfig, pObject)
    }

    class SipgateStringSerializer : TypeSerializerImpl() {
        @Throws(SAXException::class)
        override fun write(handler: org.xml.sax.ContentHandler, `object`: Any) =
                write(handler, "string", "string", `object`.toString())
    }
}