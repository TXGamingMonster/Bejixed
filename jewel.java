import java.awt.image.BufferedImage;
import java.awt.*;

public class jewel
{
	/*
	 *x:		location on screen
	 *y:		location on screen  Remember that graphics is c, r and solution matrix will be r, c
	 *width & height are the width and height of the image
	 *image is Buffered image read in from file
	 *rectangle is Rectangle that corresponds to the jewel.
	 *
	 *You may want to add another boolean for showing (or clicked) if you choose.
	 *Another option is to compare image to graySquare for unclicked.
	 */

	private int x, y, width, height,value;
	private BufferedImage image;
	private Rectangle rectangle;
	public jewel()
	{
		x = 0;
		y = 0;
		width = 67;
		height = 67;
		image= null;
		value = 0;
		rectangle = new Rectangle(0,0,67,67);
	}

	public jewel(int x1, int y1, int w, int h,int v, BufferedImage i)
	{
		x = x1;
		y = y1;
		width = w;
		height = h;
		image = i;
		value =v;
		rectangle = new Rectangle (x,y,width,height);
	}
	
	public jewel(jewel j)
	{
		x = j.getX();
		y = j.getY();
		width = j.getWidth();
		height = j.getHeight();
		image= j.getImage();
		value = j.getValue();
		rectangle = new Rectangle (x,y,width,height);
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getValue()
	{
		return value;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public Rectangle getRectangle()
	{
		return rectangle;
	}

	public void setX(int x1)
	{
		x = x1;
	}

	public void setY(int y1)
	{
		y = y1;
	}

	public void setWidth(int w)
	{
		width = w;
	}

	public void setHeight(int h)
	{
		height = h;
	}

	public void setValue(int v)
	{
		value=v;
	}

	public void setImage(BufferedImage i)
	{
		image = i;
	}

	public void setRectangle (Rectangle r)
	{
		rectangle = r;
	}

	public String toString()
	{
		return "(" + x + ", " + y + ") ";
	}
}