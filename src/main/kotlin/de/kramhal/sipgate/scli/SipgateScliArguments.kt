package de.kramhal.sipgate.scli

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties
class SipgateScliArguments {
    var user: String = ""
    var password: String = ""
    var home: String = ""
    var target: String = ""

    override fun toString(): String {
        return "SipgateScliArguments(user='$user', password=******, home='$home', target='$target')"
    }
}