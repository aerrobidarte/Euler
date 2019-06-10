package euler2D;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Principal extends JFrame implements ActionListener{

	Sistema2D graphArea;
	JTextField inputEcuacion1;
	JTextField inputInc;
	JTextField inputEcuacion2;
	JTextField inputA;
	JTextField inputB;
	JLabel incremento;
	JLabel descA;
	JLabel descB;
	JLabel pA;
	JLabel pB;
	
	public Principal() {
		super("Euler");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel inputs = new JPanel();
		inputs.setLayout(new FlowLayout());
		inputEcuacion1 = new JTextField(10);
		inputInc = new JTextField(5);
		inputEcuacion2 = new JTextField(10);
		inputA=new JTextField(3);
		inputB=new JTextField(3);
		incremento = new JLabel();
		descA= new JLabel();
		descB = new JLabel();
		pA=new JLabel();
		pB=new JLabel();
		JButton button = new JButton("Graficar");
		
		
		incremento.setText("h:");
		descA.setText("x':");
		descB.setText("y':");
		pA.setText("A:");
		pB.setText("B:");
		
		button.addActionListener(this);
		inputs.add(incremento);
		inputs.add(inputInc);
		inputs.add(descA);
		inputs.add(inputEcuacion1);
		inputs.add(descB);
		inputs.add(inputEcuacion2);
		inputs.add(pA);
		inputs.add(inputA);
		inputs.add(pB);
		inputs.add(inputB);
		inputs.add(button);
		
		mainPanel.add(inputs, BorderLayout.NORTH);
	
		graphArea = new Sistema2D();
		mainPanel.add(graphArea);

		super.getContentPane().add(mainPanel);
		super.pack();
		super.setVisible(true);
		
		inputInc.setText("0.1");
		inputEcuacion1.setText("x-(y)");
		inputEcuacion2.setText("x");
		inputA.setText("2");
		inputB.setText("1");
		
	}
	
	public static void main(String[] args) {
		new Principal();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
			graphArea.inc = inputInc.getText();
			graphArea.equation = inputEcuacion1.getText();
			graphArea.equation1 = inputEcuacion2.getText();
			graphArea.puntoA=inputA.getText();
			graphArea.puntoB=inputB.getText();
			graphArea.repaint();
	}
}