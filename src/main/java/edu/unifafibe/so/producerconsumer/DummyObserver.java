package edu.unifafibe.so.producerconsumer;
/**
 * A do-nothing implementation of the SimulationObserver
 * class.  You can use a DummyObserver to test your
 * queue implementation within a main method.
 *
 * @param <E> type of elements stored in the queue
 */
public class DummyObserver<E> implements SimulationObserver<E> {

	public void producerWaiting() {
	}

	public void producerRunning() {
	}

	public void consumerWaiting() {
	}

	public void consumerRunning() {
	}

	public void update() {
	}

}
