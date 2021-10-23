package collisions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

public class BallClass
{
	private double x,y,r,vx,vy,m;
	Color Kolor;
	
	public BallClass(double x,double y,double r)
	{
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D oval = new Ellipse2D.Double(getX()-getR(),getY()-getR(),2*getR(),2*getR());	
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Kolor);
		g2.fill(oval);
	}
	
	public void WarunkiSprezyste()
	{
		setX(getX()+getVX());
		
		if(getX()-getR()<0)
		{
			setX(getR());
			setVX(-getVX());
		}
		if(getX()+getR()>Main.AnimacjaWidth())
		{
			setVX(-getVX());
		}
		
		setY(getY()+getVY());
		
		if(getY()-getR()<0)
		{
			setY(getR());
			setVY(-getVY());
		}
		if(getY()+getR()>Main.AnimacjaHeight())
		{
            setVY(-getVY());
		}
	}
	
	public void WarunkiNiesprezyste()
	{
		setX(getX()+getVX());
		
		if(getX()-getR()<0)
		{
			setX(getR());
			setVX(0);
		}
		if(getX()+getR()>Main.AnimacjaWidth())
		{
			setVX(0);
		}
		
		setY(getY()+getVY());
		
		if(getY()-getR()<0)
		{
			setY(getR());
			setVY(0);
		}
		if(getY()+getR()>Main.AnimacjaHeight())
		{
			setY(Main.AnimacjaHeight()-getR());
            setVY(0);
		}
	}
	
	public double getVX() 
    {
        return vx;
    }

    public void setVX(double vx) 
    {
        this.vx = vx;
    }

    public double getVY() 
    {
        return vy;
    }

    public void setVY(double vy) 
    {
        this.vy = vy;
    }

    public double getX() 
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY() 
    {
        return y;
    }

    public void setY(double y) 
    {
        this.y = y;
    }

    public double getR() 
    {
        return r;
    }

    public void setR(double r) 
    {
        this.r = r;
    }
    
    public double getM()
    {
    	return m;
    }
    
    public void setM(double m)
    {
    	this.m = m;
    }
    
    public void setBallColor(Color ballColor)
    {
    	Kolor = ballColor;
    }
}