package emejorado;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class EulerMejorado extends JComponent{
	
	//Defino cuantos pixeles es 1 unidad
	int unit = 50, invRes=10;
	String equation = "t";
	String equation1 ="x=(t*t)/2";
	String inc="0.5";
	boolean err=false;
	
	public EulerMejorado() {
		super();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		super.setPreferredSize(new Dimension((int)(screenSize.getHeight()*0.6), (int)(screenSize.getHeight()*0.6)));
	}
	
	@Override
	//Configuro la grilla y los ejes
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(Color.white);
		int width = super.getWidth();
		int height = super.getHeight();
		g2d.clearRect(0, 0, width, height);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.blue);
		g2d.drawLine(width/2, 0, width/2, height);
		g2d.drawLine(0, height/2, width, height/2);
		g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[] {10, 2}, 0));
		for(int i=width/2+unit; i<width; i+=unit)
			g2d.drawLine(i, 0, i, height);
		for(int i=width/2-unit; i>0; i-=unit)
			g2d.drawLine(i, 0, i, height);
		for(int i=height/2+unit; i<height; i+=unit)
			g2d.drawLine(0, i, width, i);
		for(int i=height/2-unit; i>0; i-=unit)
			g2d.drawLine(0, i, width, i);
		for(int i=width/2, i2=0; i<width; i+=unit, i2++)
			g2d.drawString(i2+",0", i+5, height/2-5);
		for(int i=width/2-unit, i2=-1; i>0; i-=unit, i2--)
			g2d.drawString(i2+",0", i+5, height/2-5);
		for(int i=height/2+unit, i2=-1; i<height; i+=unit, i2--)
			g2d.drawString("0,"+i2, width/2+5, i-5);
		for(int i=height/2-unit, i2=1; i>0; i-=unit, i2++)
			g2d.drawString("0,"+i2, width/2+5, i-5);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.red);
		
		DrawEquation(equation,equation1,inc, g2d, width, height);
	}
	
	private void DrawEquation(String s,String s1,String i,Graphics2D g2d, int width, int height) {
		String sides = s;
		String[] sides1 = s1.split("=");
			Point prev = null;
			//Defino resolusion 
			float res = 1.0f/invRes;
			//Obtengo el centro de la pantalla
			int mw = width/2;
			int mh = height/2;
			//Obtengo el numero de unidades en el eje horizontal.
			double mr = (double)mw/unit;
			//Grafica funcion ya integrada
			for(double a=-mr; a<=mr; a+=res) {
				double b = stringToOperation(a, sides1[1]);
				if(err)
					break;
				int a2 = (int)(a*unit + mw);
				int b2 = (int)(b*unit + mh);
				if(prev != null)
					g2d.drawLine(prev.x, prev.y, a2, height-b2);
				prev = new Point(a2, height-b2);
			}
			//Grafica por aproximacion (euler) hacia la derecha
			double yant=0;
			double xant=0;
			double f;
			prev=new Point(mw,mh);
			for(double aa=Float.parseFloat(i); aa<=mr; aa+=Float.parseFloat(i)) {
				f=yant+stringToOperationOne(xant,yant, sides)*Float.parseFloat(i);
				
				f=yant+(Float.parseFloat(i)*(stringToOperationOne(xant,yant, sides)+stringToOperationOne(aa,f, sides)))/2;
				
				int a2 = (int)(aa*unit + mw);
				int b2 = (int)(f*unit + mh);
				if(prev != null)
					g2d.drawLine(prev.x, prev.y, a2, height-b2);
				prev = new Point(a2, height-b2);
				yant=f;
				xant=aa;
			}
			
			//Grafica por aproximacion (euler) hacia la izquierda
			yant=0;
			xant=0;
			prev=new Point(mw,mh);
			for(double aa=-Float.parseFloat(i); aa>=-mr; aa-=Float.parseFloat(i)) {
				f=yant-stringToOperationOne(xant,yant, sides)*Float.parseFloat(i);
				
				f=yant-(Float.parseFloat(i)*(stringToOperationOne(xant,yant, sides)+stringToOperationOne(aa,f, sides)))/2;
				
				int a2 = (int)(aa*unit + mw);
				int b2 = (int)(f*unit + mh);
				if(prev != null)
					g2d.drawLine(prev.x, prev.y, a2, height-b2);
				prev = new Point(a2, height-b2);
				yant=f;
				xant=aa;
			}
			
		}
	//Evalua funcion integrada
	private double stringToOperation(double a, String eq) {
		double b=0;
		eq = eq.replace("t", a+"");
		eq = eq.replace("x", a+"");
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    try {
			b = ((Number) engine.eval(eq)).doubleValue();
		} catch (ScriptException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			err = true;
		}
		return b;
	}
	//Evalua fucion sin integrar por Euler
	private double stringToOperationOne(double as,double bs, String eq) {
		double b=0;
		eq = eq.replace("t", as+"");
		eq = eq.replace("x", bs+"");
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    try {
			b = ((Number) engine.eval(eq)).doubleValue();
		} catch (ScriptException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			err = true;
		}
		return b;
	}
	
	
}