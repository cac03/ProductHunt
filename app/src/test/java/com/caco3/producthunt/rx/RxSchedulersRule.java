package com.caco3.producthunt.rx;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

public class RxSchedulersRule implements TestRule {
  @Override
  public Statement apply(final Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        try {
          setUp();
          base.evaluate();
        } finally {
          tearDown();
        }
      }
    };
  }

  private void setUp() {
    RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook(){
      @Override
      public Scheduler getMainThreadScheduler() {
        return Schedulers.immediate();
      }
    });
    RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook(){
      @Override
      public Scheduler getComputationScheduler() {
        return Schedulers.immediate();
      }

      @Override
      public Scheduler getIOScheduler() {
        return Schedulers.immediate();
      }
    });
  }

  private void tearDown() {
    RxAndroidPlugins.getInstance().reset();
    RxJavaPlugins.getInstance().reset();
  }
}
