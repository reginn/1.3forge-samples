package samples.playereventsamplemod.client;

import net.minecraft.src.*;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import samples.playereventsamplemod.CommonProxy;

// クライアント側のみのクラスはこのアノテーションをつける
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	// NOOP
}