package tbh.engine;
import java.nio.IntBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
public class Window 
{
	public int width;
	public int height;
	private long handle;
	public boolean resized;
	
	public Window(int w, int h, String title)
	{
		width = w;
		height = h;
		GLFWErrorCallback.createPrint(System.err).set();
		if(!GLFW.glfwInit()) throw new IllegalStateException("GLFW is kill");
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, 0);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, 1);
		handle = GLFW.glfwCreateWindow(w, h, title, 0, 0);
		if(handle == 0)
		{
			throw new RuntimeException("GLFW is not kill. However comma, Window is kill.");
		}
		GLFW.glfwSetKeyCallback(handle, (handle, key, scancode, action, mods) -> 
		{
			if(key==GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
			{
				GLFW.glfwSetWindowShouldClose(handle, true);
			}
		});
		GLFW.glfwSetWindowSizeCallback(handle, (handle, newWidth, newHeight)->
		{
			Window.this.width = newWidth;
			Window.this.height = newHeight;
			Window.this.resized = true;
		});
		try(MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			GLFW.glfwGetWindowSize(handle, pWidth, pHeight);
			GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
			GLFW.glfwSetWindowPos(handle, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
		GLFW.glfwMakeContextCurrent(handle);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(handle);
	}
	
	public long getHandle()
	{
		return handle;
	}
	
	public void destroy()
	{
		Callbacks.glfwFreeCallbacks(handle);
		GLFW.glfwDestroyWindow(handle);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}
	
	public boolean isKeyPressed(int key)
	{
		return GLFW.glfwGetKey(handle, key) == GLFW.GLFW_PRESS;
	}
}
