package edu.unifafibe.so.producerconsumer;
/**
 * Factory class for creating new BoundedQueue objects.
 */
public class QueueFactory {
	/**
	 * Create a BoundedQueue object.
	 * 
	 * @param <E> the queue element type
	 * @param observer the QueueObserver that should be informed when the
	 *                 producer and consumer are running or waiting
	 * @param maxItems maximum number of items that are allowed in the
	 *                 queue at one time
	 * @return a BoundedQueue object
	 */
	public static<E> BoundedQueue<E> createQueue(SimulationObserver<E> observer, int maxItems) {
		return new MyQueue<E>(observer, maxItems);
	}
}
