package com.stringconcat.dev.course.clinic

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
internal class MainControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    fun getHelloResponseContent(): String {
        return mockMvc.get("/hello")
            .andExpect { status { is2xxSuccessful() } }
            .andReturn()
            .response.contentAsString
    }

    @Test
    fun hello() {
        assertEquals("""Hello there""", getHelloResponseContent())
    }

    @Test
    fun helloFalse() {
        assertNotEquals("""Hello ther""", getHelloResponseContent())
    }
}
