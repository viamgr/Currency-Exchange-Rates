package app.vahid.datasource.remote.base

import app.vahid.common.core.errors.ForbiddenError
import app.vahid.common.core.errors.ItemNotFound
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.every
import io.mockk.mockk
import retrofit2.Response

class HttpErrorMapperBehaviorSpec : BehaviorSpec() {
    init {

        Given("an http error mapper") {
            val httpErrorMapper = HttpErrorMapper()

            When("call parse method") {
                And("response is successful") {
                    val response = mockk<Response<Any>>()
                    every { response.code() } returns 200

                    val result = httpErrorMapper.parse(response)
                    Then("output should be null") {
                        result.shouldBeNull()
                    }
                }
                And("response is 403") {
                    val response = mockk<Response<Any>>()
                    every { response.code() } returns 403

                    val result = httpErrorMapper.parse(response)

                    Then("output should be an ForbiddenError error") {
                        result.shouldBeInstanceOf<ForbiddenError>()
                    }
                }
                And("response is 404") {
                    val response = mockk<Response<Any>>()
                    every { response.code() } returns 404

                    val result = httpErrorMapper.parse(response)

                    Then("output should be an ItemNotFound error") {
                        result.shouldBeInstanceOf<ItemNotFound>()
                    }
                }

            }
        }
    }
}
