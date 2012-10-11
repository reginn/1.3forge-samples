package samples.containersamplemod;

import net.minecraft.src.*;

import cpw.mods.fml.common.network.IGuiHandler;

import samples.containersamplemod.client.GuiNoop;

public class CommonProxy implements IGuiHandler
{
	public void registerTextures()
	{
		// サーバー側では何もしない
	}
	
	/*
	 IGuiHandlerの実装
	 クライアント側はGuiScreenやGuiContainerを,
	 サーバー側はContainerをそれぞれ返す
	*/
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == ContainerSampleMod.guiIdContainerNoop)
		{
			return new GuiNoop(player, world, x, y, z);
		}
		
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == ContainerSampleMod.guiIdContainerNoop)
		{
			return new ContainerNoop(player, world, x, y, z);
		}
		
		return null;
	}
	
}