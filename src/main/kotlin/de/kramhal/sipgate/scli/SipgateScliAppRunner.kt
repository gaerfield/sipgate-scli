package de.kramhal.sipgate.scli

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class SipgateScliAppRunner : ApplicationRunner {
    companion object {
        val LOG = LoggerFactory.getLogger(SipgateScliAppRunner::class.java)
    }

    override fun run(args: ApplicationArguments?) {
        LOG.warn("Here I am")
        RestfulClient().getEntity()
    }
}