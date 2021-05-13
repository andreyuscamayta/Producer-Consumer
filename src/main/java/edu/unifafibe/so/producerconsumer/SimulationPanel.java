package edu.unifafibe.so.producerconsumer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Create, execute, and visualize the producer/consumer simulation.
 */
public class SimulationPanel extends JPanel {

	static class QueueItem {
		int shape;
		Color color;
		
		public QueueItem(int shape, Color color) {
			this.shape= shape;
			this.color= color;
		}
		public int getShape() {
			return shape;
		}
		public Color getColor() {
			return color;
		}
	}
	
	class MyObserver implements SimulationObserver<QueueItem> {

		public void producerWaiting() {
			if (simulation.getProducer().changeState(Worker.WAITING))
				updateView();
		}

		public void producerRunning() {
			if (simulation.getProducer().changeState(Worker.RUNNING))
				updateView();
		}

		public void consumerWaiting() {
			if (simulation.getConsumer().changeState(Worker.WAITING))
				updateView();
		}

		public void consumerRunning() {
			if (simulation.getConsumer().changeState(Worker.RUNNING))
				updateView();
			
		}
		
		public void update() {
			updateView();
		}
	}
	
	class MyProducer extends Producer<QueueItem> {
		MyProducer(BoundedQueue<QueueItem> queue) {
			super(queue);
		}
		
		protected QueueItem createItem() {
			int shape = random.nextInt(2);
			Color color;
			switch (random.nextInt(3)) {
			case 0: color = Color.RED; break;
			case 1: color = Color.GREEN; break;
			default: color = Color.BLUE; break;
			}
			return new QueueItem(shape, color);
		}
	}
	
	class MyConsumer extends Consumer<QueueItem> {
		MyConsumer(BoundedQueue<QueueItem> queue) {
			super(queue);
		}
	}
	
	private static final int MAX_ITEMS = 17;
	private static final int SHAPE_WIDTH = 18;
	private static final int QUEUE_WIDTH = 340;
	private static final int SHAPE_SEPARATOR = 2;
	
	private static final Font MSG_FONT = new Font("Sans", Font.BOLD, 20);
	
	Random random;
	Simulation<QueueItem> simulation;
	
	public SimulationPanel() {
		random = new Random();
		createSimulation();
	}
	
	public void updateView() {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				repaint();
			}
		});
	}

	private void createSimulation() {
		simulation = new Simulation<QueueItem>();
		SimulationObserver<QueueItem> observer = new MyObserver();
		BoundedQueue<QueueItem> queue = QueueFactory.createQueue(observer, MAX_ITEMS);
		simulation.setQueue(queue);
		simulation.setProducer(new MyProducer(queue));
		simulation.setConsumer(new MyConsumer(queue));
	}

	public void paint(Graphics g) {
		super.paint(g);
		paintWorker(10, 20, simulation.getProducer(), g);
		paintWorker(450, 20, simulation.getConsumer(), g);
		paintQueue(100, 38, g);
	}

	private void paintWorker(int x, int y, Worker<QueueItem> worker, Graphics g) {
		g.setFont(MSG_FONT);
		
		g.setColor(Color.BLACK);
		g.drawString(worker.getName(), x, y);
		y += 20;
		
		drawQueueItem(worker.getCurrentItem(), x + 25, y, g);
		y += (SHAPE_WIDTH + 30);
		
		g.setColor(Color.BLACK);
		g.drawString("" + worker.getTimeLeft(), x, y);
		y += 25;
		
		String stateDescr = worker.getState() == Worker.RUNNING ? "Running" : "Waiting";
		g.drawString(stateDescr, x, y);
	}

	private void drawQueueItem(QueueItem currentItem, int x, int y, Graphics g) {
		if (currentItem == null) return;
		g.setColor(currentItem.getColor());
		switch (currentItem.getShape()) {
		case 0:
			g.fillRect(x, y, SHAPE_WIDTH, SHAPE_WIDTH);
			break;
		default:
			g.fillOval(x, y, SHAPE_WIDTH, SHAPE_WIDTH);
			break;
		}
	}

	private void paintQueue(int x, int y, Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(x, y, x + QUEUE_WIDTH, y);
		g.drawLine(
				x,
				y+SHAPE_WIDTH+(SHAPE_SEPARATOR*2),
				x+QUEUE_WIDTH,
				y+SHAPE_WIDTH+(SHAPE_SEPARATOR*2));

		List<QueueItem> queueContents = new LinkedList<QueueItem>();
		simulation.getQueue().getContents(queueContents);

		x += QUEUE_WIDTH;
		for (QueueItem item : queueContents) {
			drawQueueItem(item, x - SHAPE_WIDTH, y + SHAPE_SEPARATOR, g);
			x -= (SHAPE_WIDTH + SHAPE_SEPARATOR);
		}		
	}

	public void startSimulation(int produceRate, int consumeRate) {
		if (!simulation.isRunning()) {
			simulation.getProducer().setWorkTime(produceRate);
			simulation.getConsumer().setWorkTime(consumeRate);
			simulation.start();
		}
	}

	public void stopSimulation() {
		if (simulation.isRunning()) {
			try {
				simulation.shutDown();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(this, "Unexpected InterruptedException", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			createSimulation();
			repaint();
		}
	}
}
