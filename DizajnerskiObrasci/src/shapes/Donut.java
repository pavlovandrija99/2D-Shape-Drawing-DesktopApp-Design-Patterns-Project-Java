package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {

	private int innerRadius;
	
	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) { 
		this(center, radius, innerRadius, selected);
		setColor(color);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) { 
		this(center, radius, innerRadius, selected, color);
		setInnerColor(innerColor);
	}
	
	@Override
	public void draw(Graphics g) {
		/*super.draw(g);
		g.setColor(Color.BLACK);
		g.setColor(this.getColor());
		g.drawOval(getCenter().getX() - this.innerRadius, getCenter().getY() - this.innerRadius, this.innerRadius * 2, this.innerRadius * 2);
		*/
	
		Ellipse2D.Double outerCircle = new Ellipse2D.Double(this.getCenter().getX() -
		this.getRadius(), this.getCenter().getY() - this.getRadius(),
		this.getRadius() * 2, this.getRadius() * 2); 
		
		Ellipse2D.Double innerShape = new Ellipse2D.Double(this.getCenter().getX() -
		this.getInnerRadius(), this.getCenter().getY() - this.getInnerRadius(),
		this.getInnerRadius() * 2, this.getInnerRadius() * 2);
		
		Area donut = new Area(outerCircle);
        donut.subtract(new Area(innerShape));
		
		Graphics2D graphics2d = (Graphics2D) g.create();
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		graphics2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		graphics2d.setColor(this.getInnerColor());
		graphics2d.fill(donut);
		graphics2d.setColor(this.getColor()); 
	    graphics2d.draw(donut);
	    
	    graphics2d.dispose();	
	    
	    if(isSelected()) {
	    	g.setColor(Color.BLUE);
			g.drawRect(super.getCenter().getX() - 3, super.getCenter().getY() - 3, 6, 6);
			g.drawRect(super.getCenter().getX() - radius - 3, super.getCenter().getY() - 3, 6, 6);
			g.drawRect(super.getCenter().getX() + radius - 3, super.getCenter().getY() - 3, 6, 6);
			g.drawRect(super.getCenter().getX() - 3, super.getCenter().getY() - radius - 3, 6, 6);
			g.drawRect(super.getCenter().getX() - 3, super.getCenter().getY() + radius - 3, 6, 6);
	    }
	}
	
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.WHITE);
		g.fillOval(getCenter().getX() - this.innerRadius, getCenter().getY() - this.innerRadius, this.innerRadius * 2, this.innerRadius * 2);
	
	}
	
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			if (this.getCenter().equals(d.getCenter()) &&
					this.getRadius() == d.getRadius() &&
					this.innerRadius == d.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}
	
	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	
	public int getInnerRadius() {
		return this.innerRadius;
	}
	
	public void setInnerRadius(int innerRadius) throws Exception{
		if(innerRadius>0)
		{
		this.innerRadius = innerRadius;
		}
		else
		{
			throw new NumberFormatException("innerRadius has not to be a value greater then 0");
		}
	}
	
	
	public Donut clone(Donut d) {
		d.getCenter().setX(this.getCenter().getX());
		d.getCenter().setY(this.getCenter().getY());
		try {
			d.setRadius(this.getRadius());
			d.setInnerRadius(this.getInnerRadius());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		d.setColor(this.getColor());
		d.setInnerColor(this.getInnerColor());
		
		return d;
	}
	
	
	@Override
	public String toString() {
		return "Donut: (" + getCenter().getX() + ", " + getCenter().getY() + "), " + "Radius=" + radius + ", Inner Radius=" + 
				this.innerRadius + ", Inner Color: (" + Integer.toString(super.getInnerColor().getRGB()) + ")" + ", Border Color: ("
					 + Integer.toString(super.getColor().getRGB()) + ")";
	}
	
}
