package com.example.connectivity_kit.data.di;

import android.content.Context;
import android.net.ConnectivityManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class ConnectivityModule_Companion_ProvideConnectivityManagerFactory implements Factory<ConnectivityManager> {
  private final Provider<Context> contextProvider;

  public ConnectivityModule_Companion_ProvideConnectivityManagerFactory(
      Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ConnectivityManager get() {
    return provideConnectivityManager(contextProvider.get());
  }

  public static ConnectivityModule_Companion_ProvideConnectivityManagerFactory create(
      Provider<Context> contextProvider) {
    return new ConnectivityModule_Companion_ProvideConnectivityManagerFactory(contextProvider);
  }

  public static ConnectivityManager provideConnectivityManager(Context context) {
    return Preconditions.checkNotNullFromProvides(ConnectivityModule.Companion.provideConnectivityManager(context));
  }
}
