package tbh.engine;

import java.util.HashMap;

public class Options 
{
	public HashMap<String, Object> options = new HashMap<String, Object>();
	public Options()
	{
		options.put("fps", 60);
		options.put("fullscreen", false);
	}
	public void setOption(String key, String value)
	{
		if(options.get(key) != null)
		{
			options.replace(key, value);
		}
		else
		{
			options.put(key, value);
		}
	}
}
