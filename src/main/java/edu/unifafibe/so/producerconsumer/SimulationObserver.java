package edu.unifafibe.so.producerconsumer;
/**
 * A SimulationObserver should be notified when the status of
 * the Producer and Consumer change between running and waiting.
 *
 * @param <E> queue element type
 */
public interface SimulationObserver<E> {
	/**
	 * Notify the observer that the Producer is waiting.
	 */
	public void producerWaiting();
	
	/**
	 * Notify the observer that the Producer is running.
	 */
	public void producerRunning();
	
	/**
	 * Notify the observer that the Consumer is waiting.
	 */
	public void consumerWaiting();
	
	/**
	 * Notify the observer is the Consumer is running.
	 */
	public void consumerRunning();

	/**
	 * Notify the observer that some state change
	 * has occurred.
	 */
	public void update();
}
