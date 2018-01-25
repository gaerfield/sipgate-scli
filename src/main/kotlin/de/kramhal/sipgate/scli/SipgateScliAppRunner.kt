package de.kramhal.sipgate.scli

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
class SipgateScliAppRunner : ApplicationRunner {

    @ConfigurationProperties
    open class SipgateScliArguments {
        var nutzer: String = ""
        var passwort: String = ""
        var tel: String = ""
        var anruf: String = ""

        override fun toString(): String {
            return "SipgateScliArguments(nutzer='$nutzer', passwort='$passwort', tel='$tel', anruf='$anruf')"
        }
    }

    companion object {
        val LOG = LoggerFactory.getLogger(SipgateScliAppRunner::class.java)
    }

    override fun run(args: ApplicationArguments?) {
        LOG.warn("Here I am")
        SipgateRpcClient().printArgs()
    }
}