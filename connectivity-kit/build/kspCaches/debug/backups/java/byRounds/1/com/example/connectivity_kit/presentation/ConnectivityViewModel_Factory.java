package com.example.connectivity_kit.presentation;

import com.example.connectivity_kit.domain.ObserveNetworkStatusUseCase;
import com.example.connectivity_kit.domain.PerformSpeedTestUseCase;
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
public final class ConnectivityViewModel_Factory implements Factory<ConnectivityViewModel> {
  private final Provider<ObserveNetworkStatusUseCase> observeNetworkStatusUseCaseProvider;

  private final Provider<PerformSpeedTestUseCase> performSpeedTestUseCaseProvider;

  public ConnectivityViewModel_Factory(
      Provider<ObserveNetworkStatusUseCase> observeNetworkStatusUseCaseProvider,
      Provider<PerformSpeedTestUseCase> performSpeedTestUseCaseProvider) {
    this.observeNetworkStatusUseCaseProvider = observeNetworkStatusUseCaseProvider;
    this.performSpeedTestUseCaseProvider = performSpeedTestUseCaseProvider;
  }

  @Override
  public ConnectivityViewModel get() {
    return newInstance(observeNetworkStatusUseCaseProvider.get(), performSpeedTestUseCaseProvider.get());
  }

  public static ConnectivityViewModel_Factory create(
      Provider<ObserveNetworkStatusUseCase> observeNetworkStatusUseCaseProvider,
      Provider<PerformSpeedTestUseCase> performSpeedTestUseCaseProvider) {
    return new ConnectivityViewModel_Factory(observeNetworkStatusUseCaseProvider, performSpeedTestUseCaseProvider);
  }

  public static ConnectivityViewModel newInstance(
      ObserveNetworkStatusUseCase observeNetworkStatusUseCase,
      PerformSpeedTestUseCase performSpeedTestUseCase) {
    return new ConnectivityViewModel(observeNetworkStatusUseCase, performSpeedTestUseCase);
  }
}
