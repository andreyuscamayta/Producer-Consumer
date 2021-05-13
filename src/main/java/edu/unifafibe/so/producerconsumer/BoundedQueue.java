package edu.unifafibe.so.producerconsumer;


import java.util.List;

/**
 * A bounded queue that may be used by multiple threads.
 *
 * @param <E> type of element to be stored in the queue
 */
public interface BoundedQueue<E> {
	/**
	 * Enqueue an item.  If the queue is full (contains the maximum
	 * number of items), then the calling thread should wait until
	 * the queue contains less than the maximum number of items.</p>
	 * 
	 * <p>This method should call the SimulationObserver's <b>producerWaiting</b>
	 * and <b>producerRunning</b> methods to reflect whether the calling
	 * thread is running or is waiting.
	 * 
	 * @param item the item to add to the queue
	 * @throws InterruptedException 
	 */
	public void enqueue(E item) throws InterruptedException;

	/**
	 * Dequeue an item.  If the queue is empty, then the calling thread
	 * should wait until the queue contains at least one item.
	 * 
	 * <p>This method should call the SimulationObserver's <b>consumerWaiting</b>
	 * and <b>consumerRunning</b> methods to reflect whether the calling
	 * thread is running or is waiting.
	 * 
	 * @return the item removed from the head of the queue
	 * @throws InterruptedException
	 */
	public E dequeue() throws InterruptedException;
	
	/**
	 * Get a reference to the SimulationObserver.
	 * 
	 * @return the SimulationObserver
	 */
	public SimulationObserver<E> getObserver();
	
	/**
	 * Get the entire contents of the queue.
	 * The first item added to the argument list should be the
	 * head item, and the last item added to the argument should
	 * be the tail item.
	 * 
	 * @param queueContents a List to which the queue contents should be added
	 */
	public void getContents(List<E> queueContents);
}
