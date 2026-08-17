package com.example.connectivity_kit.presentation;

import com.example.connectivity_kit.domain.ObserveNetworkStatusUseCase;
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

  public ConnectivityViewModel_Factory(
      Provider<ObserveNetworkStatusUseCase> observeNetworkStatusUseCaseProvider) {
    this.observeNetworkStatusUseCaseProvider = observeNetworkStatusUseCaseProvider;
  }

  @Override
  public ConnectivityViewModel get() {
    return newInstance(observeNetworkStatusUseCaseProvider.get());
  }

  public static ConnectivityViewModel_Factory create(
      Provider<ObserveNetworkStatusUseCase> observeNetworkStatusUseCaseProvider) {
    return new ConnectivityViewModel_Factory(observeNetworkStatusUseCaseProvider);
  }

  public static ConnectivityViewModel newInstance(
      ObserveNetworkStatusUseCase observeNetworkStatusUseCase) {
    return new ConnectivityViewModel(observeNetworkStatusUseCase);
  }
}
