package de.kramhal.sipgate.scli.services

class GetServerInfo : SipgateService<GetServerInfo.ServerInfoResult> {
    override val methodName: String
        get() = "system.serverInfo"
    override val params: Map<String, Any>
        get() = emptyMap()

    data class ServerInfo(val specVersion: String, val name: String, val version: String, val vendor: String)
    data class ServerInfoResult(val result: Any) : SipgateServiceResult {
        override val status: SipgateServiceResult.Status
            get() = super.buildStatus(result)

        val serverInfo: ServerInfo = ServerInfo(
                (result as Map<String, Any>).get("SpecificationVersion") as String,
                result.get("ServerName") as String,
                result.get("ServerVersion") as String,
                result.get("ServerVendor") as String
        )

    }

    override fun convert(result: Any) = ServerInfoResult(result)
}

class GetMethods : SipgateService<GetMethods.MethodList> {
    override val methodName: String
        get() = "system.listMethods"
    override val params: Map<String, Any>
        get() = emptyMap()

    data class MethodList(val result: Any) : SipgateServiceResult {
        override val status: SipgateServiceResult.Status
            get() = super.buildStatus(result)
        val methods = ((result as Map<String, Any>).get("listMethods") as Array<Any>)
                .map { a -> a as String }
                .toList()
    }

    override fun convert(result: Any) = MethodList(result)
}

class GetMethodHelp(val method: String) : SipgateService<GetMethodHelp.MethodHelp> {
    override val methodName: String
        get() = "system.methodHelp"
    override val params: Map<String, Any>
        get() = mapOf("MethodName" to method)

    data class MethodHelp(val result: Any) : SipgateServiceResult {
        override val status: SipgateServiceResult.Status
            get() = super.buildStatus(result)
        val message: String
            get() {
                val bla = (result as Map<String, Any>).get("methodHelp")
                if (bla is String)
                    return bla
                else
                    return ""
            }
    }

    override fun convert(result: Any) = MethodHelp(result)
}

class GetMethodSignature(val method: String) : SipgateService<GetMethodSignature.MethodSignature> {
    override val methodName: String
        get() = "system.methodSignature"
    override val params: Map<String, Any>
        get() = mapOf("MethodName" to method)

    data class MethodSignature(val result: Any) : SipgateServiceResult {
        override val status: SipgateServiceResult.Status
            get() = super.buildStatus(result)
        val signatures: List<List<String>>
            get() {
                val arrayOfArrayOfAnys = ((result as Map<String, Any>).get("methodSignature")) as Array<Any>
                val listOfArrayOfAnys = arrayOfArrayOfAnys.map { it as Array<Any> }.toList()
                val listOfListOfStrings = listOfArrayOfAnys.map { el -> el.map { any -> any as String }.toList() }.toList()
                return listOfListOfStrings
            }
    }

    override fun convert(result: Any) = MethodSignature(result)
}

