package samples.villagersamplemod;

import net.minecraft.src.*;

// FMLのログで使用
import java.util.logging.Level;

// FMLのログに出力するAPI
import cpw.mods.fml.common.FMLLog;

// Forge式コンフィグファイルを使うためのAPI
import net.minecraftforge.common.Configuration;

// プロキシ指定用アノテーション
import cpw.mods.fml.common.SidedProxy;

// Modコンテナ設定用アノテーション
import cpw.mods.fml.common.Mod;

// NetworkModコンテナ設定用アノテーション
import cpw.mods.fml.common.network.NetworkMod;

// FMLのイベント
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

// 村人に関するAPI
import cpw.mods.fml.common.registry.VillagerRegistry;

// modid   : このmod固有の文字列
// name    : modの名前
// version : バージョン
@Mod(modid = "VillagerSampleMod", name = "Villager Sample Mod", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class VillagerSampleMod
{
	// このクラスのインスタンスを生成, アノテーションの引数はmodid
	@Mod.Instance("VillagerSampleMod") 
	public static VillagerSampleMod instance;
	
	// クライアント側とサーバー側で異なる処理を一意的に行うためのプロキシシステム
	// ここの引数のパッケージ名はフルパス
	@SidedProxy(clientSide = "samples.villagersamplemod.client.ClientProxy", serverSide = "samples.villagersamplemod.CommonProxy")
	public static CommonProxy proxy;
	
	// 前処理, 主にコンフィグファイルの設定
	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		/*
		 event.getSuggestedConfigurationFile()でmodid.cfgのFileObjectが返ってくる
		 なおConfiguration自体の引数はFile
		*/
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			
			// 今回は何もしない
			
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Error Massage");
		}
		finally
		{
			cfg.save();
		}
	}
	
	// 従来のloadに相当
	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		/*
			引数は
			villagerType,
			IVillageTradeHandler
			villagerTypeは
			0 : farmer     (茶色の服)
			1 : libirarian (白い服)
			2 : priest     (紫色の服)
			3 : smith      (黒い前掛け)
			4 : buther     (白い前掛け)
			5以降はVillagerRegistryで追加した村人
		*/
		VillagerRegistry.instance().registerVillageTradeHandler(2, new SampleVillageTradeHandler());
	}
}