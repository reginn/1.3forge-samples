package samples.mouseclicksamplemod;

import net.minecraft.src.*;

import cpw.mods.fml.common.network.IGuiHandler;

import samples.mouseclicksamplemod.client.GuiMouseClick;

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
		if (ID == MouseClickSampleMod.guiIdMouseClick)
		{
			return new GuiMouseClick(player, world, x, y, z);
		}
		
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == MouseClickSampleMod.guiIdMouseClick)
		{
			return new ContainerMouseClick(player, world, x, y, z);
		}
		
		return null;
	}
	
}