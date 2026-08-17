package com.example.connectivity_kit.data;

import android.net.ConnectivityManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class ConnectivityRepositoryImpl_Factory implements Factory<ConnectivityRepositoryImpl> {
  private final Provider<ConnectivityManager> connectivityManagerProvider;

  private final Provider<SpeedTestEngine> speedTestEngineProvider;

  public ConnectivityRepositoryImpl_Factory(
      Provider<ConnectivityManager> connectivityManagerProvider,
      Provider<SpeedTestEngine> speedTestEngineProvider) {
    this.connectivityManagerProvider = connectivityManagerProvider;
    this.speedTestEngineProvider = speedTestEngineProvider;
  }

  @Override
  public ConnectivityRepositoryImpl get() {
    return newInstance(connectivityManagerProvider.get(), speedTestEngineProvider.get());
  }

  public static ConnectivityRepositoryImpl_Factory create(
      Provider<ConnectivityManager> connectivityManagerProvider,
      Provider<SpeedTestEngine> speedTestEngineProvider) {
    return new ConnectivityRepositoryImpl_Factory(connectivityManagerProvider, speedTestEngineProvider);
  }

  public static ConnectivityRepositoryImpl newInstance(ConnectivityManager connectivityManager,
      SpeedTestEngine speedTestEngine) {
    return new ConnectivityRepositoryImpl(connectivityManager, speedTestEngine);
  }
}
