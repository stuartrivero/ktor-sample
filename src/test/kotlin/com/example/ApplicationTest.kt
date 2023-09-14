package com.example

import com.example.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }

    @Test
    fun testGetCustomers() = testApplication {
        val response = client.get("/customer")
        assertEquals(
            """No customers found""", response.bodyAsText()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun testGetCustomerWithNoId() = testApplication {
        val response = client.get("/customer/")
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun testGetCustomerWithIdButNoCustomer() = testApplication {
        val response = client.get("/customer/200")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }
}
