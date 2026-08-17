package com.example.connectivity_kit.data;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class SpeedTestEngine_Factory implements Factory<SpeedTestEngine> {
  @Override
  public SpeedTestEngine get() {
    return newInstance();
  }

  public static SpeedTestEngine_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SpeedTestEngine newInstance() {
    return new SpeedTestEngine();
  }

  private static final class InstanceHolder {
    private static final SpeedTestEngine_Factory INSTANCE = new SpeedTestEngine_Factory();
  }
}
