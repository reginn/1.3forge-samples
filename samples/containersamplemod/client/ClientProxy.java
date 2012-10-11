package samples.containersamplemod.client;

import net.minecraft.src.*;

import net.minecraftforge.client.MinecraftForgeClient;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import samples.containersamplemod.CommonProxy;

// クライアント側のみのクラス
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	public void registerTextures()
	{
		// スプライトIDの再割り当てを行いたい画像の先行読み込み
		MinecraftForgeClient.preloadTexture("/samples/sprites/blocks.png");
	}
	
}