package shapes;

import java.awt.Color;
import java.awt.Graphics;

import shapes.Point;
import shapes.Shape;

public class Point extends Shape implements Cloneable {
	
	private int x;
	private int y;
	
	
	public Point()
	{
		
	}
	
	public Point(int x,int y)
	{
		this.x=x;
		this.y=y;
		
	}
	
	
	public Point(int x,int y,boolean selected)
	{
		this(x,y);
		this.setSelected(selected);
	}
	
	
	public Point(int x,int y,boolean selected,Color color)
	{
		this(x,y,selected);
		setColor(color);
	}
	
	
	@Override
	public void moveBy(int byX, int byY) {
		this.x = this.x + byX;
		this.y += byY;
		
	}
	
		
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		g.drawLine(this.getX()-3,this.getY(), this.getX()+3,this.getY());
		g.drawLine(this.getX(), this.getY()-3, this.getX(), this.getY()+3);
		if(isSelected())
		{
			g.setColor(Color.blue);
			g.drawRect(this.x-3, this.y-3, 6, 6);
			
			
		}
		
	}
	
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Point)
		{
			Point p=new Point(0,0);
			return (int)(this.distance(p.getX(), p.getY())-((Point)o).distance(p.getX(), p.getY()));
		}
		return 0;
	}
	
	
	
	public double distance(int x2,int y2)
	{
		int dx=this.x-x2;
		int dy=this.y-y2;
		
		double dis=Math.sqrt(dx*dx+dy*dy);
		return dis;
				
	}
	

	public boolean equals(Object obj)
	{
		if(obj instanceof Point)
		{
			Point pomocna=(Point)obj;
			if(pomocna.getX()==this.x && pomocna.getY() == this.y )
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	
	public boolean contains(Point p)
	{
		return this.distance(p.getX(), p.getY())<3;
	}
	
	
	
	public boolean contains(int x, int y)
	{
		return this.distance(x, y) < 3;
	}
	
	
	
	public Point clone(Point p) {
		p.setX(this.getX());
		p.setY(this.getY());
		p.setColor(this.getColor());
		return p;
		
	}
	
	
	public int getX() {
		return this.x;
	}
	
	
	public void setX(int x) {
		this.x = x;
	}
	
	
	public int getY() {
		return this.y;
	}
	
	
	public void setY(int y) {
		this.y = y;
	}
	
	
	
	
	@Override
	public String toString()
	{
		return "Point: (" + x + ", " + y + "), " + "Color: ("+Integer.toString(getColor().getRGB())+")";
	}
	
}
