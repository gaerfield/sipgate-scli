package de.kramhal.sipgate.scli

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class SipgateScliApp(val rpcClient: SipgateRpcClient) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
//        rpcClient.printServerInfosEtc()
        rpcClient.initiateCall()
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(SipgateScliApp::class.java)
            .properties("spring.config.name:sipgate")
            .build()
            .run(*args)
}
