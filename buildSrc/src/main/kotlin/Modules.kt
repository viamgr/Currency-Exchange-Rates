import app.vahid.gradle.base.Module

sealed class Modules(override val path: String) : Module(path = path) {
    object Common : Module(":common")
    object Repository : Module(":repository")
    object BaseUiAndroidTestShared : Module(":base_ui:android_test_shared")
    object BaseUiCommon : Module(":base_ui:common")
    object BaseUiTheme : Module(":base_ui:theme")
    object CommonCore : Module(":common:core")
    object CommonDomainCommon : Module(":common:domain_common")
    object CommonPresentation : Module(":common:presentation")
    object CommonTestShared : Module(":common:test_shared")
    object DatasourceCache : Module(":datasource:cache")
    object DatasourceRemote : Module(":datasource:remote")
    object DomainGateway : Module(":domain:gateway")
    object DomainUseCase : Module(":domain:use_case")
    object FeatureCurrencyExchangePresentation : Module(":feature:currency_exchange:presentation")
    object FeatureCurrencyExchangeUi : Module(":feature:currency_exchange:ui")
}
 