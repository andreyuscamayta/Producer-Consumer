package edu.unifafibe.so.producerconsumer;
import java.util.Random;

/**
 * Worker class.
 * Implements some common functionality used by both Producers
 * and Consumers.
 *
 * @param <E> element type
 */
public abstract class Worker<E> implements Runnable {
	public static final int RUNNING = 0;
	public static final int WAITING = 1;

	private Random random;

	protected BoundedQueue<E> queue;
	protected int workTime;
	protected E currentItem;
	protected int timeLeft;
	protected int state;
	protected boolean running;
	protected boolean shutdownRequested;

	private Thread thread;

	/**
	 * Constructor.
	 * 
	 * @param queue the queue that will be used by this Worker
	 */
	public Worker(BoundedQueue<E> queue) {
		this.random = new Random();
		this.queue = queue;
		this.running = false;
		this.shutdownRequested = false;
	}

	/**
	 * Get the name of this worker.
	 * 
	 * @return the name
	 */
	public abstract String getName();
	
	/**
	 * Set the work time for this worker: the average number
	 * of time units to produce or consume.
	 * 
	 * @param workTime the work time
	 */
	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}
	
	/**
	 * Start the worker.  Should be called at the beginning of run().
	 */
	protected synchronized void start() {
		running = true;
		thread = Thread.currentThread();
	}

	/**
	 * Set the current item that the worker is working on.
	 * 
	 * @param item the current item
	 */
	protected synchronized void setCurrentItem(E item) {
		currentItem = item;
	}
	
	/**
	 * Get the current item that the worker is working on.
	 * 
	 * @return the current item, or null if there is no current item.
	 */
	public synchronized E getCurrentItem() {
		return currentItem;
	}

	/**
	 * Get the number of time units left before the worker either
	 * produces or consumes the current item.
	 * 
	 * @return the time left
	 */
	public synchronized int getTimeLeft() {
		return timeLeft;
	}
	
	/**
	 * Shut down the worker (and the thread that is executing it).
	 * This method waits until the worker has stopped.
	 * 
	 * @throws InterruptedException
	 */
	public void shutDown() throws InterruptedException {
		synchronized (this) {
			this.shutdownRequested = true;

			thread.interrupt();
			
			while (running) {
				this.wait();
			}
		}
	}
	
	/**
	 * Change the state of the worker.
	 * 
	 * @param state the worker's new state
	 * @return true if the worker's state changed, or false it if stayed the same
	 */
	public synchronized boolean changeState(int state) {
		boolean changed = (this.state != state);
		this.state = state;
		return changed;
	}

	/**
	 * Get the worker's state.
	 * 
	 * @return the worker's state
	 */
	public synchronized int getState() {
		return state;
	}

	/**
	 * Return whether or not the worker should keep running.
	 * 
	 * @return true if the worker should keep running,
	 *         or false if a shutdown request has been issued
	 */
	public synchronized boolean keepRunning() {
		if (shutdownRequested) {
			running = false;
			this.notifyAll();
		}
		return running;
	}

	/**
	 * Count down to a random produce/consume time.
	 * 
	 * @throws InterruptedException
	 */
	public void countDown() throws InterruptedException {
		long now = System.currentTimeMillis();
		long fireTime = now + (100 * (random.nextInt(workTime - 1) + 1));
		
		while (now < fireTime) {
			long millisLeft = fireTime - now;
			
			synchronized (this) {
				timeLeft = (int) (millisLeft / 100);
			}
			queue.getObserver().update();
			
			if (!keepRunning()) {
				return;
			}
			Thread.sleep(50L);
			
			now = System.currentTimeMillis();
		}
	}
}
