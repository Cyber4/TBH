package tbh.implementation;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import tbh.engine.EngineTemplate;
import tbh.engine.Options;

public class Engine extends EngineTemplate
{
	public static Options tbhoptions = new Options();
	
	public Engine()
	{
		super();
	}
	
	public Engine(String yo)
	{
		super(yo);
	}
	
	public Engine(int width, int height, String title)
	{
		super(width, height, title);
	}
	
	@Override
	public void prerender() 
	{
		GL11.glClearColor(0.5f, 0.75f, 1f, 1f);
	}
	
	@Override
	public void handleInput() 
	{
	
	}
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public void render() 
	{
		if(window.resized)
		{
			GL11.glViewport(0, 0, window.width, window.height);
		}
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GLFW.glfwSwapBuffers(this.window.getHandle());
		GLFW.glfwPollEvents();
	}
	
	@Override
	public void debug()
	{
		System.out.println("FPS: " + this.getFPS());
		System.out.println("TPS: " + this.getTPS());
	}
	
	@Override
	public void cleanup() 
	{
		this.window.destroy();
		gameTimer.cancel();
		
	}
}
