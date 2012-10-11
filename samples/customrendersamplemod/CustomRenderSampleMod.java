package samples.customrendersamplemod;

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

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

// modid   : このmod固有の文字列
// name    : modの名前
// version : バージョン
@Mod(modid = "CustomRenderSampleMod", name = "CustomRender Sample Mod", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class CustomRenderSampleMod
{
	// このクラスのインスタンスを生成, アノテーションの引数はmodid
	@Mod.Instance("CustomRenderSampleMod") 
	public static CustomRenderSampleMod instance;
	
	// クライアント側とサーバー側で異なる処理を一意的に行うためのプロキシシステム
	// ここの引数のパッケージ名はフルパス
	@SidedProxy(clientSide = "samples.customrendersamplemod.client.ClientProxy", serverSide = "samples.customrendersamplemod.CommonProxy")
	public static CommonProxy proxy;
	
	public static Block blockNoop;
	
	private int blockIdBlockNoop = 4021;
	
	public static int blockNoopRenderType;
	
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
		blockNoopRenderType = proxy.getNewRenderType();
		
		blockNoop = (new BlockNoop(blockIdBlockNoop, 1)).setBlockName("customsample");
		
		GameRegistry.registerBlock(blockNoop);
		
		GameRegistry.addShapelessRecipe(
			new ItemStack(blockNoop, 2),
				new Object[]
				{
					Block.stone
				});
		
		LanguageRegistry.instance().addNameForObject(blockNoop, "en_US", "Custom Render Sample Block");
		
		proxy.registerRenderers();
	}
}