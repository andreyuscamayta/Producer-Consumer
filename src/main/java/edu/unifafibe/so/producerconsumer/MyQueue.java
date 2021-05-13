package edu.unifafibe.so.producerconsumer;
import java.util.ArrayList;
import java.util.List;

public class MyQueue<E> implements BoundedQueue<E> {
	// You shouldn't need to add any fields
	private final Object lock = new Object();
	private final List<E> data;
	private final int maxItems;
	private final SimulationObserver<E> observer;

	public MyQueue(SimulationObserver<E> observer, int maxItems) {
		// You shouldn't need to change the constructor
		data = new ArrayList<E>();
		this.maxItems = maxItems;
		this.observer = observer;
	}

	@Override
	public void enqueue(E item) throws InterruptedException {		
		throw new UnsupportedOperationException("TODO - implement");
	}

	@Override
	 public E dequeue() throws InterruptedException {
		throw new UnsupportedOperationException("TODO - implement");
	}

	@Override
	public SimulationObserver<E> getObserver() {
		return observer;
	}

	@Override
	public void getContents(List<E> queueContents) {
		throw new UnsupportedOperationException("TODO - implement");
	}
}
