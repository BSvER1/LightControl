import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;


@SuppressWarnings("serial")
public class InstallationPreviewWindow extends Canvas implements Runnable{
	
	Thread lightUpdater;
	
	static boolean running;
	
	int numVertexes = 11;
	
	double scale;
	int xOffset = 40;
	int yOffset = 15;
	
	
	public InstallationPreviewWindow() {
		
		
		setSize(500,500);
		scale = (double) 500/275;
		
		setupStrips();
		
		System.out.println("Starting thread.");
		
		//start thread
		lightUpdater = new Thread(this);
		lightUpdater.setName("Preview Updater");
		running = true;
		lightUpdater.start();
		
		System.out.println("Thread started.");
		
		invalidate();
	}


	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;//per second
		double timePerTick = 1000000000/amountOfTicks;
		double delta = 0;
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(2);
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
				if (LightControlWindow.viewTabs.getSelectedComponent().getName() != null 
						&& LightControlWindow.viewTabs.getSelectedComponent().getName().equals("Launchpad View")) {
					render(bs);
				}
				delta--;
			}
			
		}
		
		bs.dispose();
	}
	
	public void render(BufferStrategy bs) {
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) getBounds().getWidth(), (int) getBounds().getHeight());
		
		for (int i = 0; i<LightControlWindow.getLightData().getNumStrips(); i++) {
			g.setColor(LightControlWindow.getLightData().getStrip(i).getStripColor());
			g.drawLine((int) (LightControlWindow.getLightData().getStrip(i).getStart().getLoc().getX()*scale + xOffset), 
					(int) (LightControlWindow.getLightData().getStrip(i).getStart().getLoc().getY()*scale + yOffset),
					(int) (LightControlWindow.getLightData().getStrip(i).getStop().getLoc().getX()*scale + xOffset), 
					(int) (LightControlWindow.getLightData().getStrip(i).getStop().getLoc().getY()*scale + yOffset));
		}
		
		g.dispose();
		
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
	
	public static void setRunning(boolean newRunning) {
		running = newRunning;
	}
}
