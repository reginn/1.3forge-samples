package samples.entitylivingsamplemod.client;

import net.minecraft.src.*;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

// レンダーに関するレジストリ
import cpw.mods.fml.client.registry.RenderingRegistry;

import samples.entitylivingsamplemod.CommonProxy;
import samples.entitylivingsamplemod.EntityMobSample;

// クライアント側のみのクラスはこのアノテーションをつける
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	public void registerRenderers()
	{
		// ModLoader.addRendererでのmap.putに相当
		// Entityのクラスと描画, モデルを結びつける
		// 今回はゾンビのレンダーをそのまま使用
		RenderingRegistry.registerEntityRenderingHandler(EntityMobSample.class, new RenderBiped(new ModelZombie(), 0.5F));
	}

}