package com.liferay.launchpad.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;

import jodd.mutable.MutableInteger;

import jodd.util.ThreadUtil;

import org.junit.Test;

/**
 * This test actually shows behavior of CompletableFuture important for
 * the client.
 */
public class CompletableFutureTest {

	/**
	 * Even though it is a completed future, apply() will be executed
	 * but in the same thread as the main thread!
	 */
	@Test
	public void testCompleted_thenApplyException_thenApply() {
		final MutableInteger access = new MutableInteger(0);

		System.out.println(Thread.currentThread().getId());
		CompletableFuture<Integer> f =
			CompletableFuture.completedFuture(new MutableInteger(1))
			.thenApply(new Function<MutableInteger, MutableInteger>() {
				@Override
				public MutableInteger apply(MutableInteger mi) {
					System.out.println(Thread.currentThread().getId());
					throw new IllegalArgumentException();
				}
			})
			.thenApply((mi) -> {
				access.value++;
				return Integer.valueOf(mi.value++);
			});

		try {
			f.join();
			fail();
		}
		catch (CompletionException ce) {
			assertTrue(ce.getCause() instanceof IllegalArgumentException);
			assertEquals(0, access.value);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testSupply_thenApplyException_thenApply() {
		final MutableInteger access = new MutableInteger(0);

		System.out.println(Thread.currentThread().getId());
		CompletableFuture<Integer> f =
			CompletableFuture.supplyAsync(() -> {
				System.out.println(Thread.currentThread().getId());
				ThreadUtil.sleep(500);
				access.value++;
				return new MutableInteger(1);
			})
			.thenApply(new Function<MutableInteger, MutableInteger>() {
				@Override
				public MutableInteger apply(MutableInteger mi) {
					System.out.println(Thread.currentThread().getId());
					throw new IllegalArgumentException();
				}
			})
			.thenApply((mi) -> {
				access.value++;
				return Integer.valueOf(mi.value++);
			});

		try {
			f.join();
			fail();
		}
		catch (CompletionException ce) {
			assertTrue(ce.getCause() instanceof IllegalArgumentException);
			assertEquals(1, access.value);
		}
		catch (Exception e) {
			fail();
		}
	}

}