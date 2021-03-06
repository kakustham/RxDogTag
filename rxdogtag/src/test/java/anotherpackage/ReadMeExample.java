/*
 * Copyright (C) 2019. Uber Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package anotherpackage;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import rxdogtag2.RxDogTag;

/** This is the code for the README examples. Un-ignore to test yourself. */
@Ignore
@SuppressWarnings("CheckReturnValue")
public class ReadMeExample {

  @Rule
  public AggressiveUncaughtExceptionHandlerRule handlerRule =
      new AggressiveUncaughtExceptionHandlerRule();

  @After
  public void tearDown() {
    RxDogTag.reset();
  }

  @Test
  public void simpleSubscribe() {
    RxDogTag.install();
    Observable.error(new RuntimeException("Unhandled error!")).subscribe();
  }

  @Test
  public void complex() throws InterruptedException {
    RxDogTag.install();
    CountDownLatch latch = new CountDownLatch(1);
    Observable.just(1).subscribeOn(Schedulers.io()).map(i -> null).subscribe();
    latch.await(1, TimeUnit.SECONDS);
  }

  @Test
  public void complexDelegate() throws InterruptedException {
    RxDogTag.install();
    CountDownLatch latch = new CountDownLatch(1);
    Observable.just(1).subscribeOn(Schedulers.io()).subscribe(i -> throwSomething());
    latch.await(1, TimeUnit.SECONDS);
  }

  private void throwSomething() {
    throw new RuntimeException("Unhandled error!");
  }
}
