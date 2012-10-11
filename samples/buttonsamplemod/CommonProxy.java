package samples.buttonsamplemod;

import net.minecraft.src.*;

import cpw.mods.fml.common.network.IGuiHandler;

import samples.buttonsamplemod.client.GuiButtonNoop;

public class CommonProxy implements IGuiHandler
{
	public void registerTextures()
	{
		// サーバー側では何もしない
	}
	
	/*
	 IGuiHandlerの実装
	 クライアント側はGuiScreenを,
	 サーバー側はContainerをそれぞれ返す
	*/
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == ButtonSampleMod.guiIdButtonNoop)
		{
			return new GuiButtonNoop(player, world, x, y, z);
		}
		
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == ButtonSampleMod.guiIdButtonNoop)
		{
			return new ContainerButtonNoop(player, world, x, y, z);
		}
		
		return null;
	}
	
}