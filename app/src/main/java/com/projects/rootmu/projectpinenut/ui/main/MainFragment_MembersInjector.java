package com.projects.rootmu.projectpinenut.ui.main;

import com.projects.rootmu.projectpinenut.utils.CheckAppStart;
import com.projects.rootmu.projectpinenut.utils.SharedPreferencesManager;
import dagger.MembersInjector;
import dagger.internal.InjectedFieldSignature;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainFragment_MembersInjector implements MembersInjector<MainFragment> {
  private final Provider<SharedPreferencesManager> sharedPreferencesManagerProvider;

  private final Provider<CheckAppStart> checkAppStartProvider;

  public MainFragment_MembersInjector(
      Provider<SharedPreferencesManager> sharedPreferencesManagerProvider,
      Provider<CheckAppStart> checkAppStartProvider) {
    this.sharedPreferencesManagerProvider = sharedPreferencesManagerProvider;
    this.checkAppStartProvider = checkAppStartProvider;
  }

  public static MembersInjector<MainFragment> create(
      Provider<SharedPreferencesManager> sharedPreferencesManagerProvider,
      Provider<CheckAppStart> checkAppStartProvider) {
    return new MainFragment_MembersInjector(sharedPreferencesManagerProvider, checkAppStartProvider);
  }

  @Override
  public void injectMembers(MainFragment instance) {
    injectSharedPreferencesManager(instance, sharedPreferencesManagerProvider.get());
    injectCheckAppStart(instance, checkAppStartProvider.get());
  }

  @InjectedFieldSignature("com.projects.rootmu.projectpinenut.ui.main.MainFragment.sharedPreferencesManager")
  public static void injectSharedPreferencesManager(MainFragment instance,
      SharedPreferencesManager sharedPreferencesManager) {
    instance.sharedPreferencesManager = sharedPreferencesManager;
  }

  @InjectedFieldSignature("com.projects.rootmu.projectpinenut.ui.main.MainFragment.checkAppStart")
  public static void injectCheckAppStart(MainFragment instance, CheckAppStart checkAppStart) {
    instance.checkAppStart = checkAppStart;
  }
}
