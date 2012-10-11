package samples.entitylivingsamplemod;

import java.util.logging.Level;

import net.minecraft.src.*;

// Forge式コンフィグファイルを利用するためのAPI
import net.minecraftforge.common.Configuration;

// FMLのログに出力するAPI
import cpw.mods.fml.common.FMLLog;

// プロキシシステムのためのアノテーション
import cpw.mods.fml.common.SidedProxy;

// FMLにロードさせるためのアノテーション
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkMod;

// 前初期化, 初期化のイベント
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

// Entityに関するレジストリ
import cpw.mods.fml.common.registry.EntityRegistry;

// 言語に関するレジストリ
import cpw.mods.fml.common.registry.LanguageRegistry;

// ModID   : mod固有の文字列, 
// name    : modの名前
// version : バージョン
@Mod(modid = "EntityLivingSampleMod", name = "EntityLiving Sample Mod", version = "1.0.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class EntityLivingSampleMod
{
	// クライアント側とサーバー側で異なるインスタンスを生成
	@SidedProxy(clientSide = "samples.entitylivingsamplemod.client.ClientProxy", serverSide = "samples.entitylivingsamplemod.CommonProxy")
	public static CommonProxy proxy;
	
	// 自身のインスタンス
	@Mod.Instance("EntityLivingSampleMod")
	public static EntityLivingSampleMod instance;
	
	private int entityIdEntityMobSample;
	
	// 前処理
	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			// generalカテゴリに設定可能項目を追加
			entityIdEntityMobSample = cfg.getOrCreateIntProperty("entity.id.EntityLivingSample", Configuration.CATEGORY_GENERAL, 240).getInt();
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
	
	// load()に相当
	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		// ModLoader.registerEntityに相当
		// 最後2つの引数は卵の斑の色と卵の色(RGB表記)
		// EntityIDは EntityRegistry.findGlobalUniqueEntityId()で空いてるIDをとってきてもよいが, SMPでの一致が保証されない(と思う)
		EntityRegistry.registerGlobalEntityID(EntityMobSample.class, "sample", entityIdEntityMobSample, 0xFFFFFF, 0x000000);
		
		/*
		 サーバーとクライアントのエンティティを同期させるメソッド
		 各引数はそれぞれ以下のとおり
		 Entityのクラス, 
		 Entityの内部名, 
		 このmod内で使用する同期取り用のID,
		 @Modのクラス(ここに書くのであればthis, そうでないならinstanceを参照)
		 更新可能な距離
		 更新頻度(tickごと)
		 Entityが速度情報を持つかどうか
		*/
		EntityRegistry.registerModEntity(EntityMobSample.class, "sample", 0, this, 250, 5, true);
		
		// モンスターをスポーンさせる
		EntityRegistry.addSpawn(EntityMobSample.class, 20, 1, 4, EnumCreatureType.monster, BiomeGenBase.plains);
		
		// サーバー側は何もしない, クライアント側ではレンダーの登録が行われる
		proxy.registerRenderers();
		
		// Entityに表示される名前をつける
		LanguageRegistry.instance().addStringLocalization("entity.sample.name", "en_US", "Sample Entity Mob");
		
	}
	
}