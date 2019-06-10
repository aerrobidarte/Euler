package emejorado;


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

	EulerMejorado graphArea;
	JTextField inputInc;
	JTextField inputEcuacion;
	JTextField inputEcOrigen;
	
	JLabel incremento;
	JLabel desc;
	JLabel funcion;
	
	public Principal() {
		super("Euler");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel inputs = new JPanel();
		inputInc = new JTextField(5);
		inputEcuacion = new JTextField(10);
		inputEcOrigen = new JTextField(10);
		incremento = new JLabel();
		desc= new JLabel();
		funcion = new JLabel();
		JButton button = new JButton("Graficar");
		
		incremento.setText("h:");
		desc.setText("dx/dt:");
		funcion.setText("F-Integ:");
		
		button.addActionListener(this);
		inputs.add(incremento);
		inputs.add(inputInc);
		inputs.add(desc);
		inputs.add(inputEcuacion);
		inputs.add(funcion);
		inputs.add(inputEcOrigen);
		inputs.add(button);
		
		mainPanel.add(inputs, BorderLayout.NORTH);
	
		graphArea = new EulerMejorado();
		mainPanel.add(graphArea);

		super.getContentPane().add(mainPanel);
		super.pack();
		super.setVisible(true);
		
		inputInc.setText("0.5");
		inputEcuacion.setText("t");
		inputEcOrigen.setText("x=(t*t)/2");
		
	}
	
	public static void main(String[] args) {
		new Principal();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
			graphArea.inc = inputInc.getText();
			graphArea.equation = inputEcuacion.getText();
			graphArea.equation1 = inputEcOrigen.getText();
			graphArea.repaint();
	}
}