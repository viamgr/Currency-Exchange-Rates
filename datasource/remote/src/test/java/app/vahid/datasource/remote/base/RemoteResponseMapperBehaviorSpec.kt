package app.vahid.datasource.remote.base

import app.vahid.common.core.WrappedResult
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.every
import io.mockk.mockk
import retrofit2.Response

class RemoteResponseMapperBehaviorSpec : BehaviorSpec() {
    override fun isolationMode() = IsolationMode.InstancePerTest

    init {

        Given("a remote response mapper") {
            val remoteResponseMapper = RemoteResponseMapper(
                apiErrorMapper = mockk(),
                httpErrorMapper = mockk()
            )

            When("call map method") {

                And("response is successful") {
                    val mockkResponse = mockk<Response<Any>>()
                    val baseResponse = mockk<Any>()

                    every { mockkResponse.isSuccessful } returns true
                    every { mockkResponse.body() } returns baseResponse

                    val result: WrappedResult<Any> = remoteResponseMapper.map {
                        mockkResponse
                    }

                    Then("result should be successful") {
                        result.isSuccess.shouldBeTrue()
                    }
                }

            }

            And("response is failed") {
                val mockkResponse = mockk<Response<Any>>()
                every { mockkResponse.isSuccessful } returns false

                val result: WrappedResult<Any> = remoteResponseMapper.map {
                    mockkResponse
                }
                Then("result should not be successful") {
                    result.isSuccess.shouldBeFalse()
                }
            }

        }
    }
}
