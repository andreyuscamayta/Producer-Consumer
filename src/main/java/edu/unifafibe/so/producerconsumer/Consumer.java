package edu.unifafibe.so.producerconsumer;
/**
 * A Consumer: consumes items from the queue.
 *
 * @param <E> queue element type
 */
public class Consumer<E> extends Worker<E> {
	/**
	 * Constructor.
	 * 
	 * @param queue the queue that the consumer will fetch items from
	 */
	public Consumer(BoundedQueue<E> queue) {
		super(queue);
	}
	
	public String getName() {
		return "Consumer";
	}

	public void run() {
		start();
		
		while (keepRunning()) {

			try {
				E item = queue.dequeue();
				setCurrentItem(item);
				queue.getObserver().update();
				
				countDown();
				
				setCurrentItem(null);
				queue.getObserver().update();
			} catch (InterruptedException e) {
				// ignore for now: will check at top of loop
			}
		}
	}

}
