package com.stringconcat.dev.course.clinic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class ClinicApplication

fun main(args: Array<String>) {
    runApplication<ClinicApplication>(*args)
}

@RestController
class MainController {

    @GetMapping(path = ["/hello"])
    fun hello(): String {
        return """Hello world"""
    }

}