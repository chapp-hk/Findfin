# Hilt extension annotation processors

KSP annotation processors to reduce @Binds boilerplate
e.g.
````kotlin
@Module
@InstallIn(SingletonComponent::class)
interface SomeHiltModule {

  @Binds
  fun bindSome(impl: SomeImpl): SomeInterface

}
````
