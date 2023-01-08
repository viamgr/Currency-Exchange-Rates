package app.vahid

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.vahid.common.core.requireThrowable
import app.vahid.repository.datasource.CurrencyExchangeRemoteDataSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RemoteApiInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var currencyExchangeRemoteDataSource: CurrencyExchangeRemoteDataSource

    @Before
    fun prepareTest() {
        hiltRule.inject()
    }

    @Test
    fun checkNetworkRequestIsWorkingProperly() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        val items = currencyExchangeRemoteDataSource.getCurrencyRateList()
        if (!items.isSuccess) {
            items.requireThrowable().printStackTrace()
        }

        assert(items.isSuccess)

        activityScenario.close()
    }
}
