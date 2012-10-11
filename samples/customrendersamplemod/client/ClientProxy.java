package samples.customrendersamplemod.client;

import net.minecraft.src.*;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import cpw.mods.fml.client.registry.RenderingRegistry;

import samples.customrendersamplemod.CommonProxy;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	public int getNewRenderType()
	{
		// 新しいレンダータイプを取得
		return RenderingRegistry.getNextAvailableRenderId();
	}
	
	public void registerRenderers()
	{
		// カスタムレンダリングのインスタンスをRenderingRegistryに登録する
		RenderingRegistry.registerBlockHandler(new SampleBlockRenderingHandler());
	}
	
}