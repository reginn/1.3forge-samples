package samples.villagersamplemod.client;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import samples.villagersamplemod.CommonProxy;

// クライアント側のみのクラスにはこのアノテーションをつける
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	// 何もしない
}