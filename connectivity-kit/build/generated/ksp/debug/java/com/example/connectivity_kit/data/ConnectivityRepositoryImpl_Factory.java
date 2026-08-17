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

  public ConnectivityRepositoryImpl_Factory(
      Provider<ConnectivityManager> connectivityManagerProvider) {
    this.connectivityManagerProvider = connectivityManagerProvider;
  }

  @Override
  public ConnectivityRepositoryImpl get() {
    return newInstance(connectivityManagerProvider.get());
  }

  public static ConnectivityRepositoryImpl_Factory create(
      Provider<ConnectivityManager> connectivityManagerProvider) {
    return new ConnectivityRepositoryImpl_Factory(connectivityManagerProvider);
  }

  public static ConnectivityRepositoryImpl newInstance(ConnectivityManager connectivityManager) {
    return new ConnectivityRepositoryImpl(connectivityManager);
  }
}
