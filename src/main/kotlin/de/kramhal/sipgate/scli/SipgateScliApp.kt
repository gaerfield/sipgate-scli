package de.kramhal.sipgate.scli

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SipgateScliApp

fun main(args: Array<String>) {
    SpringApplication.run(SipgateScliApp::class.java, *args)
}