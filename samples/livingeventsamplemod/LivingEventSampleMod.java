package samples.livingeventsamplemod;

import net.minecraft.src.*;

import net.minecraftforge.common.MinecraftForge;

// プロキシシステムのためのアノテーション
import cpw.mods.fml.common.SidedProxy;

// FMLにロードさせるためのアノテーション
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkMod;

// 前初期化, 初期化のイベント
import cpw.mods.fml.common.event.FMLInitializationEvent;

// ModID   : mod固有の文字列, 
// name    : modの名前
// version : バージョン
@Mod(modid = "LivingEventSampleMod", name = "LivingEvent Sample Mod", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class LivingEventSampleMod
{
	// クライアント側とサーバー側で異なるインスタンスを生成
	@SidedProxy(clientSide = "samples.livingeventsamplemod.client.ClientProxy", serverSide = "samples.livingeventsamplemod.CommonProxy")
	public static CommonProxy proxy;
	
	// 自身のインスタンス
	@Mod.Instance("LivingEventSampleMod")
	public static LivingEventSampleMod instance;
	
	// load()に相当
	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new LivingEventSampleHooks());
	}
	
}