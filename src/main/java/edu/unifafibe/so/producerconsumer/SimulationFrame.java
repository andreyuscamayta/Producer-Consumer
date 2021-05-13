package edu.unifafibe.so.producerconsumer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class SimulationFrame extends JFrame {

	private JPanel jContentPane = null;
	private JButton startButton = null;
	private JButton stopButton = null;
	private JButton quitButton = null;
	private SimulationPanel simulationPanel = null;
	private JTextArea produceTimeTextArea = null;
	private JTextArea consumeTimeTextArea = null;
	private JLabel produceRateLabel = null;
	private JLabel consumeRateLabel = null;

	public SimulationFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("Producer/Consumer Simulation");
		this.setPreferredSize(new java.awt.Dimension(600,440));
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setBounds(new java.awt.Rectangle(0,0,603,229));
		this.setResizable(true);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			consumeRateLabel = new JLabel();
			consumeRateLabel.setBounds(new java.awt.Rectangle(428,175,96,15));
			consumeRateLabel.setText("Consume:");
			produceRateLabel = new JLabel();
			produceRateLabel.setBounds(new java.awt.Rectangle(277,175,90,15));
			produceRateLabel.setText("Produce:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getStartButton(), null);
			jContentPane.add(getStopButton(), null);
			jContentPane.add(getQuitButton(), null);
			jContentPane.add(getSimulationPanel(), null);
			jContentPane.add(getProduceTimeTextArea(), null);
			jContentPane.add(getConsumeTimeTextArea(), null);
			jContentPane.add(produceRateLabel, null);
			jContentPane.add(consumeRateLabel, null);
		}
		return jContentPane;
	}

	private JButton getStartButton() {
		if (startButton == null) {
			startButton = new JButton();
			startButton.setText("Start");
			startButton.setSize(new java.awt.Dimension(81,33));
			startButton.setLocation(new java.awt.Point(6,165));
			startButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int produceRate = Integer.parseInt(produceTimeTextArea.getText());
					int consumeRate = Integer.parseInt(consumeTimeTextArea.getText());
					if (produceRate > 0 && consumeRate > 0) {
						simulationPanel.startSimulation(produceRate, consumeRate);
					}
				}
			});
		}
		return startButton;
	}

	private JButton getStopButton() {
		if (stopButton == null) {
			stopButton = new JButton();
			stopButton.setText("Stop");
			stopButton.setSize(new java.awt.Dimension(81,33));
			stopButton.setLocation(new java.awt.Point(93,165));
			stopButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					simulationPanel.stopSimulation();
				}
			});
		}
		return stopButton;
	}

	private JButton getQuitButton() {
		if (quitButton == null) {
			quitButton = new JButton();
			quitButton.setText("Quit");
			quitButton.setSize(new java.awt.Dimension(81,33));
			quitButton.setLocation(new java.awt.Point(183,165));
			quitButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return quitButton;
	}

	private SimulationPanel getSimulationPanel() {
		if (simulationPanel == null) {
			simulationPanel = new SimulationPanel();
			simulationPanel.setBounds(new java.awt.Rectangle(5,5,580,155));
		}
		return simulationPanel;
	}
	
	private JTextArea getProduceTimeTextArea() {
		if (produceTimeTextArea == null) {
			produceTimeTextArea = new JTextArea();
			produceTimeTextArea.setBounds(new java.awt.Rectangle(370,171,50,20));
			produceTimeTextArea.setText("20");
		}
		return produceTimeTextArea;
	}

	private JTextArea getConsumeTimeTextArea() {
		if (consumeTimeTextArea == null) {
			consumeTimeTextArea = new JTextArea();
			consumeTimeTextArea.setBounds(new java.awt.Rectangle(527,171,50,20));
			consumeTimeTextArea.setText("20");
		}
		return consumeTimeTextArea;
	}

	public static void main(String[] args) {
		SimulationFrame frame = new SimulationFrame();
		frame.setVisible(true);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
