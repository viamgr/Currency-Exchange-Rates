package app.vahid.datasource.remote.base

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ApiErrorMapperBehaviorSpec : BehaviorSpec() {
    init {
        Given("an api error mapper") {
            val apiErrorMapper = ApiErrorMapper()

            When("call parse method") {
                And("response is successful") {
                    val response = mockk<Response<Any>>()
                    every { response.errorBody() } returns null

                    val result = apiErrorMapper.parse(response)
                    Then("output should be null") {
                        assertNull(result)
                    }
                }
                And("response is failed") {
                    val response = mockk<Response<Any>>()
                    val errorBody = mockk<ResponseBody>()
                    val errorText = "An error happened"

                    every { errorBody.string() } returns errorText
                    every { response.errorBody() } returns errorBody

                    val result = apiErrorMapper.parse(response)

                    Then("output should be an error happened message") {
                        assertEquals(errorText, result?.error)
                    }
                }

            }
        }
    }
}
