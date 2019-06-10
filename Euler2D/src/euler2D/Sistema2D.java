package euler2D;

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

public class Sistema2D extends JComponent{

	int unit = 50, invRes=10;
	String equation = "x-(y)";
	String equation1 ="x";
	String inc="0.1";
	boolean err=false;
	
	String puntoA="2";
	String puntoB="1";
	
	public Sistema2D() {
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
		
		DrawEquation(equation,equation1,inc, g2d, width, height,puntoA,puntoB);
	}
	
	private void DrawEquation(String s,String s1,String i,Graphics2D g2d, int width, int height,String puntoA,String puntoB) {
		String sides = s;
		String sides1 = s1;
			Point prev = null;
			
			//determino el centro de la pantalla en pixel
			int mw = width/2;
			int mh = height/2;
			
			//Obtengo los numeros de unidades referido al eje x
			double mr = (double)mw/unit;
			
			//Obtengo Punto A(x) e B(y)
			double xant=Float.parseFloat(puntoA);
			double yant=Float.parseFloat(puntoB);
			
			//Paso los puntos A y B a pixel
			int pxa=(int)(Float.parseFloat(puntoA)*unit + mw);
			int pxb=(int)(height-(Float.parseFloat(puntoB)*unit + mh));
			
			double f;
			double g;
			prev=new Point(pxa,pxb);
			
			for(double aa=xant; aa<=mr; aa+=Float.parseFloat(i)) {
				f=xant+stringToOperationOne(xant,yant, sides)*Float.parseFloat(i);
				g=yant+stringToOperationOne(xant,yant, sides1)*Float.parseFloat(i);
				int a2 = (int)(f*unit + mw);
				int b2 = (int)(g*unit + mh);
				if(prev != null)
					g2d.drawLine(prev.x, prev.y, a2, height-b2);
				prev = new Point(a2, height-b2);
				yant=g;
				xant=f;
			}
			
			//Reseteo el 
			xant=Float.parseFloat(puntoA);
			yant=Float.parseFloat(puntoB);
			//Paso los puntos A y B a pixel
			pxa=(int)(Float.parseFloat(puntoA)*unit + mw);
			pxb=(int)(height-(Float.parseFloat(puntoB)*unit + mh));
		
			prev=new Point(pxa,pxb);
			
			//Grafica aproximacion por euler hacia la izquierda
			for(double aa=xant; aa>=-mr; aa-=Float.parseFloat(i)) {
				f=xant-stringToOperationOne(xant,yant, sides)*Float.parseFloat(i);
				g=yant-stringToOperationOne(xant,yant, sides1)*Float.parseFloat(i);
				int a2 = (int)(f*unit + mw);
				int b2 = (int)(g*unit + mh);
				if(prev != null)
					g2d.drawLine(prev.x, prev.y, a2, height-b2);
				prev = new Point(a2, height-b2);
				yant=g;
				xant=f;
			}
		}
	
	private double stringToOperationOne(double as,double bs, String eq) {
		double b=0;
		eq = eq.replace("x", as+"");
		eq = eq.replace("y", bs+"");
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