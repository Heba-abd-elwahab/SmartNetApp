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
public final class ObserveNetworkStatusUseCase_Factory implements Factory<ObserveNetworkStatusUseCase> {
  private final Provider<ConnectivityRepository> repositoryProvider;

  public ObserveNetworkStatusUseCase_Factory(Provider<ConnectivityRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveNetworkStatusUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveNetworkStatusUseCase_Factory create(
      Provider<ConnectivityRepository> repositoryProvider) {
    return new ObserveNetworkStatusUseCase_Factory(repositoryProvider);
  }

  public static ObserveNetworkStatusUseCase newInstance(ConnectivityRepository repository) {
    return new ObserveNetworkStatusUseCase(repository);
  }
}
