package tbh.engine;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public abstract class EngineTemplate
{
	public Timer gameTimer;
	public Timer debugTimer;
	public Window window;
	protected int frames;
	private int ticks;
	private int framerate;
	private int tickrate;
	
	public EngineTemplate()
	{
		this(1024, 768, "TBH");
	}
	
	public EngineTemplate(String title)
	{
		this(1024, 768, title);
	}
	
	public EngineTemplate(int width, int height, String title)
	{
		window = new Window(width, height, title);
		gameTimer = new Timer();
		gameTimer.schedule(new TimerTask()
		{
			@Override
			public void run() 
			{
				EngineTemplate.this.ticks++;
				handleInput();
				update();
			}
		}, 0, 50);
		debugTimer = new Timer();
		debugTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				tickrate = ticks;
				ticks = 0;
				framerate = frames;
				frames = 0;
				debug();
			}
		}, 0, 1000);
		GL.createCapabilities();
		prerender();
		while(!GLFW.glfwWindowShouldClose(window.getHandle()))
		{
			frames++;
			render();
		}
		gameTimer.cancel();
		debugTimer.cancel();
		cleanup();
	}
	
	public int getFPS()
	{
		return framerate;
	}
	
	public int getTPS()
	{
		return tickrate;
	}

	public abstract void prerender();
	
	public abstract void handleInput();
	
	public abstract void update();
	
	public abstract void render();
	
	public abstract void debug();
	
	public abstract void cleanup();
}
