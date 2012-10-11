package samples.customrendersamplemod;

import net.minecraft.src.*;

public class CommonProxy
{
	public int getNewRenderType()
	{
		return -1;
	}
	
	public void registerRenderers()
	{
		// サーバー側では何もしない
	}
	
}