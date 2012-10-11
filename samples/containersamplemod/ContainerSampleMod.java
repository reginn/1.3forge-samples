package samples.containersamplemod;

import net.minecraft.src.*;

// FMLのログに出力するAPI
import cpw.mods.fml.common.FMLLog;

// FMLのログで使用
import java.util.logging.Level;

// Forge式コンフィグファイルを利用するためのAPI
import net.minecraftforge.common.Configuration;

// プロキシシステムのためのアノテーション
import cpw.mods.fml.common.SidedProxy;

// FMLにロードさせるためのアノテーション
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkMod;

// 前初期化, 初期化のイベント
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

// ゲーム全般に関するレジストリ
import cpw.mods.fml.common.registry.GameRegistry;

// 名称付けに関するレジストリ
import cpw.mods.fml.common.registry.LanguageRegistry;

// ネットワークに関するレジストリ, GUIで使用
import cpw.mods.fml.common.network.NetworkRegistry;

// ModID   : mod固有の文字列, 
// name    : modの名前
// version : バージョン
@Mod(modid = "ContainerSampleMod", name = "Container Sample Mod", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ContainerSampleMod
{
	@SidedProxy(clientSide = "samples.containersamplemod.client.ClientProxy", serverSide = "samples.containersamplemod.CommonProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance("ContainerSampleMod")
	public static ContainerSampleMod instance;
	
	public static Block blockNoop;
	public static int   blockIdNoop;
	
	// GUIを呼ぶためのID, このmod内での重複さえ避ければよい
	public static int guiIdContainerNoop = 1;
	
	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			// BlockIDをコンフィグで変更可能にする
			blockIdNoop = cfg.getOrCreateIntProperty("Noop", Configuration.CATEGORY_BLOCK, 4000).getInt();
			/*
			 getOrCreateBlockIdProperty(String key, int defaultId)で設定した場合, defaultIdが既に使われていたら
			 末尾(4095)から逆順に自動で割り当てるようになる.
			 SSPだけなら問題ないが, SMPを考慮する場合は注意, サーバー側とクライアント側で一致が保証されない(と思う).
			*/
		}
		catch (Exception e)
		{
			// 失敗した場合強制終了
			FMLLog.log(Level.SEVERE, e, "Error Massage");
		}
		finally
		{
			cfg.save();
		}
	}
	
	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		blockNoop = (new BlockNoop(blockIdNoop, 0)).setBlockName("sample");
		
		// ModLoader.registerBlockに相当
		GameRegistry.registerBlock(blockNoop);
		
		// ModLoader.addRecipeに相当
		GameRegistry.addRecipe(
			new ItemStack(blockNoop, 1),
				new Object[]
				{
					" X ", " X ",
					Character.valueOf('X'), Block.dirt
				});
		
		LanguageRegistry.addName(blockNoop, "Noop Block");
		
		// IGuiHandlerを実装したクラスを登録する
		// 今回はCommonProxyに実装している
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		
		// MinecraftForgeClient.preloadTextureを呼び出す
		// サーバー側では呼び出さないのでプロキシを経由
		proxy.registerTextures();
	}

}