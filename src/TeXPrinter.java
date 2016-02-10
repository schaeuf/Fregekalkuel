import java.awt.*;
import java.io.Writer;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator; 


class TeXPrinter extends Graphics{
	private BufferedWriter bwriter;
	private Dimension dim;
	TeXPrinter(Writer w, Dimension d) throws IOException{
		dim=d;
		bwriter=new BufferedWriter(w);	
		bwriter.write("\\documentclass{article}\n\\begin{document}");
		bwriter.write("\\begin{picture}("+dim.width+","+dim.height+")\n");
	};
    public Graphics create(){return null;};
    public Graphics create(int x, int y, int width, int height) {
			Graphics g = create();
			if (g == null) return null;
			g.translate(x, y);
			g.clipRect(0, 0, width, height);
			return g;
    }

    public void translate(int x, int y){};
    public Color getColor(){return null;};
    public void setColor(Color c){};
    public void setXORMode(Color c1){};
    public Font getFont(){return null;};
    public FontMetrics getFontMetrics() {
			return getFontMetrics(getFont());
    };
    public FontMetrics getFontMetrics(Font f){return null;};
    public Rectangle getClipBounds(){return null;};
    public void clipRect(int x, int y, int width, int height){};
		public void setFont(Font f){};
		public void setPaintMode(){};
    public void setClip(int x, int y, int width, int height){};
    public Shape getClip(){return null;};
    public void setClip(Shape clip){};
    public void copyArea(int x, int y, int width, int height,
				  int dx, int dy){};
    public void drawLine(int x1, int y1, int x2, int y2){
			try{
				// bwriter.write("% Linie: " + x1 + "," + y1 + "," + x2 + "," + y2 + "\n");
				bwriter.write("\\put("+
					x1 +","+
					(dim.height-y1)+"){\\line("+
					(x2-x1)+","+(y1-y2)+"){"+
					Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2)) +"}}\n");
			}catch (IOException e){};
		};
    public void fillRect(int x, int y, int width, int height){};
    public void drawRect(int x, int y, int width, int height) {
			if ((width < 0) || (height < 0)) {
		    return;
			};
			if (height == 0 || width == 0) {
		    drawLine(x, y, x + width, y + height);
			} else {
	  	  drawLine(x, y, x + width - 1, y);
		    drawLine(x + width, y, x + width, y + height - 1);
		    drawLine(x + width, y + height, x + 1, y + height);
		    drawLine(x, y + height, x, y + 1);
			}
    };
    public void clearRect(int x, int y, int width, int height){};
    public void drawRoundRect(int x, int y, int width, int height,
				       int arcWidth, int arcHeight){};
    public void fillRoundRect(int x, int y, int width, int height,
				       int arcWidth, int arcHeight){};
    public void draw3DRect(int x, int y, int width, int height,
			   boolean raised) {
			Color c = getColor();
			Color brighter = c.brighter();
			Color darker = c.darker();
	
			setColor(raised ? brighter : darker);
			drawLine(x, y, x, y + height);
			drawLine(x + 1, y, x + width - 1, y);
			setColor(raised ? darker : brighter);
			drawLine(x + 1, y + height, x + width, y + height);
			drawLine(x + width, y, x + width, y + height - 1);
			setColor(c);
    }    ;
    public void fill3DRect(int x, int y, int width, int height,
			   boolean raised) {
			Color c = getColor();
			Color brighter = c.brighter();
			Color darker = c.darker();
		
			if (!raised) {
		    setColor(darker);
			}
			fillRect(x+1, y+1, width-2, height-2);
			setColor(raised ? brighter : darker);
			drawLine(x, y, x, y + height - 1);
			drawLine(x + 1, y, x + width - 2, y);
			setColor(raised ? darker : brighter);
			drawLine(x + 1, y + height - 1, x + width - 1, y + height - 1);
			drawLine(x + width - 1, y, x + width - 1, y + height - 2);
			setColor(c);
    };
    public void drawOval(int x, int y, int width, int height){};
    public void fillOval(int x, int y, int width, int height){};
    public void drawArc(int x, int y, int width, int height,
				 int startAngle, int arcAngle){};
    public void fillArc(int x, int y, int width, int height,
				 int startAngle, int arcAngle){};
    public void drawPolyline(int xPoints[], int yPoints[],
				      int nPoints){};
    public void drawPolygon(int xPoints[], int yPoints[],
				     int nPoints){};
    public void drawPolygon(Polygon p) {
			drawPolygon(p.xpoints, p.ypoints, p.npoints);
    };

    public void fillPolygon(int xPoints[], int yPoints[],
				     int nPoints){};
    public void fillPolygon(Polygon p) {
			fillPolygon(p.xpoints, p.ypoints, p.npoints);
    };
		
    public void drawString(String str, int x, int y){
			try{
				bwriter.write("\\put("+
						x +","+
						(dim.height-y+2)+"){"+str+"}\n");
			}catch (IOException ex){}
		};

		public void drawString(AttributedCharacterIterator iterator,
                                    int x, int y){};

    public void drawChars(char data[], int offset, int length, int x, int y) {
			drawString(new String(data, offset, length), x, y);
    };

    public void drawBytes(byte data[], int offset, int length, int x, int y) {
			drawString(new String(data, 0, offset, length), x, y);
    };

    public boolean drawImage(Image img, int x, int y, 
				      ImageObserver observer){return false;};
    public boolean drawImage(Image img, int x, int y,
				      int width, int height, 
				      ImageObserver observer){return false;};
    
    public boolean drawImage(Image img, int x, int y, 
				      Color bgcolor,
				      ImageObserver observer){return false;};

     public boolean drawImage(Image img, int x, int y,
				      int width, int height, 
				      Color bgcolor,
				      ImageObserver observer){return false;};
		 
    public boolean drawImage(Image img,
				      int dx1, int dy1, int dx2, int dy2,
				      int sx1, int sy1, int sx2, int sy2,
				      ImageObserver observer){return false;};
		
    public boolean drawImage(Image img,
				      int dx1, int dy1, int dx2, int dy2,
				      int sx1, int sy1, int sx2, int sy2,
				      Color bgcolor,
				      ImageObserver observer){return false;};
    public void dispose(){};
    public void finalize() {
			dispose();
    };
    public String toString() {	
			return getClass().getName() + "[font=" + getFont() + ",color=" + getColor() + "]";
    };

    public Rectangle getClipRect() {
			return getClipBounds();
    };

		public boolean hitClip(int x, int y, int width, int height) {
			Rectangle clipRect = getClipBounds();
			if (clipRect == null) {
	  	  return true;
			}
			return clipRect.intersects(x, y, width, height);
    };

	  public Rectangle getClipBounds(Rectangle r) {
        Rectangle clipRect = getClipBounds();
			if (clipRect != null) {
	  	  r.x = clipRect.x;
		    r.y = clipRect.y;
		    r.width = clipRect.width;
		    r.height = clipRect.height;
			} else if (r == null) {
	  	  throw new NullPointerException("null rectangle parameter");
			}
        return r;
    };
		public void close () throws IOException{
			bwriter.write("\\end{picture}");
			bwriter.write("\\end{document}");
			bwriter.close();
		};
};
