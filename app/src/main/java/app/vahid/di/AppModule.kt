package app.vahid.di

import app.vahid.appinitializers.AppInitializer
import app.vahid.appinitializers.TimberInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @IntoSet
    abstract fun bindsTimberInitializer(timberInitializer: TimberInitializer): AppInitializer

}
