package edu.unifafibe.so.producerconsumer;
/**
 * Producer/consumer simulation.
 *
 * @param <E> type of elements to be used in the simulation
 */
public class Simulation<E> {
	BoundedQueue<E> queue;
	Producer<E> producer;
	Consumer<E> consumer;
	boolean running;

	/** Constructor. */
	public Simulation() {
	}
	
	/**
	 * Set the BoundedQueue object to be used by the simulation.
	 * 
	 * @param queue the BoundedQueue object
	 */
	public void setQueue(BoundedQueue<E> queue) {
		this.queue = queue;
	}
	
	/**
	 * Set the Producer object to be used by the simulation.
	 * 
	 * @param producer the Producer object
	 */
	public void setProducer(Producer<E> producer) {
		this.producer = producer;
	}
	
	/**
	 * Set the Consumer object to be used by the simulation.
	 * 
	 * @param consumer the Consumer object
	 */
	public void setConsumer(Consumer<E> consumer) {
		this.consumer = consumer;
	}
	
	/**
	 * Start the simulation.
	 */
	public void start() {
		if (running) throw new IllegalStateException("simulation is already running");
		new Thread(producer).start();
		new Thread(consumer).start();
		running = true;
	}
	
	/**
	 * Shut down the simulation.
	 * 
	 * @throws InterruptedException
	 */
	public void shutDown() throws InterruptedException {
		if (!running) throw new IllegalStateException("simulation is not running");
		producer.shutDown();
		consumer.shutDown();
		running = false;
	}
	
	/** Get the Consumer. */
	public synchronized Consumer<E> getConsumer() {
		return consumer;
	}

	/** Get the Producer. */
	public synchronized Producer<E> getProducer() {
		return producer;
	}
	
	/** Get the BoundedQueue. */
	public synchronized BoundedQueue<E> getQueue() {
		return queue;
	}

	/**
	 * Return whether or not the Simulation is running.
	 * 
	 * @return true if the Simulation is running, false if it has
	 *         not been started yet
	 */
	public boolean isRunning() {
		return running;
	}
}
