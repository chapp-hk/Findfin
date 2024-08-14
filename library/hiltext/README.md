# Hilt extension annotation processors

## @HiltExtBindModule
KSP annotation processor to generate @Binds
e.g.
````kotlin
@Module
@InstallIn(SingletonComponent::class)
interface SomeHiltModule {

  @Binds
  fun bindSome(impl: SomeImpl): SomeInterface

}
````

## @HiltExtRoomModule
KSP annotation processor to generate
