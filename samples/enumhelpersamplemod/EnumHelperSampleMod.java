package samples.enumhelpersamplemod;

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

// Enum定数を追加できるAPI
import net.minecraftforge.common.EnumHelper;

import cpw.mods.fml.common.registry.LanguageRegistry;

// modid   : このmod固有の文字列
// name    : modの名前
// version : バージョン
@Mod(modid = "EnumHelperSampleMod", name = "EnumHelper Sample Mod", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class EnumHelperSampleMod
{
	/*
	// このクラスのインスタンスを生成, アノテーションの引数はmodid
	@Mod.Instance("EnumHelperSamleMod") 
	public static EnumHelperSampleMod instance;
	
	// クライアント側とサーバー側で異なる処理を一意的に行うためのプロキシシステム
	// ここの引数のパッケージ名はフルパス
	@SidedProxy(clientSide = "samples.enumhelpersamplemod.client.ClientProxy", serverSide = "samples.enumhelpersamplemod.CommonProxy")
	public static CommonProxy proxy;
	*/
	public static Item newPickaxe;
	
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
		// ForgeのEnumHelperの機能により新しいEnum定数"NEW_MATERIAL"を追加, 引数はEnumToolMaterialを参照のこと
		EnumToolMaterial newMaterial = EnumHelper.addToolMaterial("NEW_MATERIAL", 3, 512, 10.0F, 2, 22);
		newPickaxe = (new ItemPickaxeNoop(9000, newMaterial)).setIconCoord(2, 6).setItemName("enumhelpersample");
		
		LanguageRegistry.instance().addNameForObject(newPickaxe, "en_US", "Sample Pickaxe");
	}
}