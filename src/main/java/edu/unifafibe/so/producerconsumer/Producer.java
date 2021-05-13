package edu.unifafibe.so.producerconsumer;
/**
 * A Producer: produces items and puts them in the queue.
 * 
 * @param <E> queue element type
 */
public abstract class Producer<E> extends Worker<E> {
	/**
	 * Constructor.
	 * 
	 * @param queue queue that the Producer will work
	 */
	public Producer(BoundedQueue<E> queue) {
		super(queue);
	}
	
	public String getName() {
		return "Producer";
	}

	public void run() {
		start();
		
		while (keepRunning()) {
			try {
				E item = createItem();
				setCurrentItem(item);
				super.countDown();
				queue.enqueue(item);
				queue.getObserver().update();
			} catch (InterruptedException e) {
				// ignore for now: will check at top of loop
			}
		}
	}
	
	protected abstract E createItem();

}
