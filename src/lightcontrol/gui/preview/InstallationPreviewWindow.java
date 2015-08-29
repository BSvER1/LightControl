package lightcontrol.gui.preview;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;

import lightcontrol.control.LightControlSequence;
import lightcontrol.enums.StripColor;
import lightcontrol.gui.LightControlWindow;
import lightcontrol.gui.TimingsThread;
import lightcontrol.gui.preview.constructs.Edge;
import lightcontrol.gui.preview.constructs.Location;
import lightcontrol.gui.preview.constructs.Strip;
import lightcontrol.gui.preview.constructs.Vertex;


@SuppressWarnings("serial")
public class InstallationPreviewWindow extends Canvas implements Runnable{
	
	Thread lightUpdater;
	
	static boolean running;
	
	int numVertexes = 11;
	
	double scale;
	int xOffset = 40;
	int yOffset = 15;
	
	LightControlSequence currentPreview = null;
	LightControlSequence queuedSequence = null;
	
	
	public InstallationPreviewWindow() {
		
		setSize(500,500);
		setMaximumSize(new Dimension(500,500));
		setMinimumSize(getMaximumSize());
		setPreferredSize(getMaximumSize());
		
		scale = (double) 500/275; //TODO make this dynamic!
		
		setupStrips(); //creates the layout of strips and adds all data into LightDataCenter
		
		System.out.println("Starting preview window thread.");
		
		//start thread
		lightUpdater = new Thread(this);
		lightUpdater.setName("Preview Updater");
		running = true;
		lightUpdater.start();
		
		invalidate();
	}


	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;//per second
		double timePerTick = 1000000000/amountOfTicks;
		double delta = 0;
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			bs = this.getBufferStrategy();
		}
		
		while (running) {
			
			long now = System.nanoTime();
			//System.out.println((double) ((now-lastTime)/timePerTick));
			delta += ((now-lastTime)/timePerTick);
			//System.out.println(delta);
			lastTime = now;
			
			if (delta > 2.5) {
				System.err.println("Dropping preview window ticks");
				delta = 1.5;
			}
			while(delta>=1){
				if (currentPreview !=null) {
					render(bs);
					currentPreview.play(TimingsThread.currentEighth + TimingsThread.currentBar*32);
				}
				
				delta--;
			}
			
		}
		
		bs.dispose();
	}
	
	public void render(BufferStrategy bs) {
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK); 
		g2d.fillRect(0, 0, (int) getBounds().getWidth(), (int) getBounds().getHeight());
		
		//update light data for current sequence then display it
		
		if (LightControlWindow.getViewTabs().getSelectedComponent().getName() != null 
				&& LightControlWindow.getViewTabs().getSelectedComponent().getName().equals("Launchpad View")) {
			currentPreview.preview(TimingsThread.currentEighth + TimingsThread.currentBar*32);
		}
		
		Stroke orig = g2d.getStroke();
		g2d.setStroke(new BasicStroke(3));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i = 0; i<LightControlWindow.getLightData().getNumStrips(); i++) {
			if (!LightControlWindow.getLightData().getStrip(i).getStripColor().equals(StripColor.OFF.toColor())) {
				g2d.setColor(LightControlWindow.getLightData().getStrip(i).getStripColor());
				g2d.drawLine((int) (LightControlWindow.getLightData().getStrip(i).getStart().getLoc().getX()*scale + xOffset), 
						(int) (LightControlWindow.getLightData().getStrip(i).getStart().getLoc().getY()*scale + yOffset),
						(int) (LightControlWindow.getLightData().getStrip(i).getStop().getLoc().getX()*scale + xOffset), 
						(int) (LightControlWindow.getLightData().getStrip(i).getStop().getLoc().getY()*scale + yOffset));
			}
		}
			
		g2d.setStroke(orig);
		g.dispose();
		g2d.dispose();
		
		bs.show();
	}
	
	public void setupStrips() {
		Edge tempEdge;
		Vertex start, stop;
		
		// 1-2 top clockwise
		start = new Vertex(1, new Location(170, 9));
		stop = new Vertex(2, new Location(100, 9));
		tempEdge = new Edge(start, stop);
		LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
		
		//2-3
		start = stop;
		stop = new Vertex(3, new Location(41.1123, 46.8449));
		tempEdge = new Edge(start, stop);
		LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
		
		//3-4
		start = stop;
		stop = new Vertex(4, new Location(12.0332, 110.519));
		tempEdge = new Edge(start, stop);
		LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
		
		//4-5
		start = stop;
		stop = new Vertex(5, new Location(21.9952, 179.807));
		tempEdge = new Edge(start, stop);
		LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
		
		//5-6
		start = stop;
		stop = new Vertex(6, new Location(67.8355, 232.709));
		tempEdge = new Edge(start, stop);
		LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
		
		//6-7
		start = stop;
		stop = new Vertex(7, new Location(135, 252.43));
		tempEdge = new Edge(start, stop);
		LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
		
		//7-8
		start = stop;
		stop = new Vertex(8, new Location(202.165, 232.709));
		tempEdge = new Edge(start, stop);
		LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
		
		//8-9
		start = stop;
		stop = new Vertex(9, new Location(248.005, 179.807));
		tempEdge = new Edge(start, stop);
		LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
		
		//9-10
		start = stop;
		stop = new Vertex(10, new Location(257.967, 110.519));
		tempEdge = new Edge(start, stop);
		LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
		
		//10-11
		start = stop;
		stop = new Vertex(11, new Location(228.888, 46.8449));
		tempEdge = new Edge(start, stop);
		LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
		
		String strip;
		
		if (numVertexes % 2 == 1) {
			for(int i = 1; i <= numVertexes-2; i++) {
				for (int j = i+2; j <= numVertexes; j++) {
					strip = "" + i + "-" + (i+1);
					start = LightControlWindow.getLightData().getStrip(strip).getStart();
					strip = "" + (j-1) + "-" + j;
					stop = LightControlWindow.getLightData().getStrip(strip).getStop();
					tempEdge = new Edge(start, stop);
					LightControlWindow.getLightData().addStrip(new Strip(tempEdge));
					
					//System.out.println("Adding strip "+ start.getID() + " to " + +stop.getID());
				}
			}
		}
		
		
	}
	
	public void setCurrentSequence(LightControlSequence currentPreview) {
		this.currentPreview = currentPreview;
	}
	
	public LightControlSequence getCurrentPreview() {
		return currentPreview;
	}
	
	public void setQueuedSequence(LightControlSequence queue) {
		queuedSequence = queue;
	}
	
	public LightControlSequence getQueuedSequence() {
		return queuedSequence;
	}
	
	public static void setRunning(boolean newRunning) {
		running = newRunning;
	}
}
