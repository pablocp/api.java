package com.liferay.app.api;

/**
 * Simple entry tuple.
 */
public class Entry<K, V> {
	public Entry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Returns entry key.
	 */
	public K key() {
		return key;
	}

	/**
	 * Returns entry value.
	 */
	public V value() {
		return value;
	}

	/**
	 * Entry consumer.
	 */
	public interface EntryConsumer<K, V> {
		void accept(K key, V value);
	}

	private final K key;
	private final V value;

}