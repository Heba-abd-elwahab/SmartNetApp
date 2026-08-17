package com.example.connectivity_kit.domain;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class PerformSpeedTestUseCase_Factory implements Factory<PerformSpeedTestUseCase> {
  private final Provider<ConnectivityRepository> repositoryProvider;

  public PerformSpeedTestUseCase_Factory(Provider<ConnectivityRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PerformSpeedTestUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static PerformSpeedTestUseCase_Factory create(
      Provider<ConnectivityRepository> repositoryProvider) {
    return new PerformSpeedTestUseCase_Factory(repositoryProvider);
  }

  public static PerformSpeedTestUseCase newInstance(ConnectivityRepository repository) {
    return new PerformSpeedTestUseCase(repository);
  }
}
