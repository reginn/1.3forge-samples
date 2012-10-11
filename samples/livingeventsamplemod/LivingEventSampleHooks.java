package samples.livingeventsamplemod;

import net.minecraft.src.*;

// ログ出力用
import cpw.mods.fml.common.FMLLog;

// イベント指定のためのアノテーション
import net.minecraftforge.event.ForgeSubscribe;

// 各イベントクラス
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingEventSampleHooks
{
	/*
	 EntityLivingに関するイベントはすべてLivingEventを継承
	 LivingEventのフィールド
	 public final EntityLiving entityLiving;
	*/
	
	/*
	 EntityLivingが攻撃したときのイベント
	 Mobが攻撃したとき, プレイヤーが攻撃したときどちらも呼ばれる
	*/
	@ForgeSubscribe
	public void onLivingAttacked(LivingAttackEvent event)
	{
		/*
		 LivingAttackEventのフィールド
		 public final DamageSource source : ダメージソース
		 public final int ammount         : ダメージ量
		*/
		World world = event.entityLiving.worldObj;
		
		if (!world.isRemote)
		{
			FMLLog.info("Called Living Attack Event, DamageSource %s : DamageAmmount %d", event.source.getDamageType(), event.ammount);
		}
	}
	
	
	/*
	 EntityLivingが死んだときのイベント
	*/
	@ForgeSubscribe
	public void onLivingDead(LivingDeathEvent event)
	{
		/*
		 LivingDeathEventのフィールド
		 public final DamageSource source : ダメージソース
		*/
		World world = event.entityLiving.worldObj;
		
		if (!world.isRemote)
		{
			FMLLog.info("Called Living Death Event, DamageSource %s", event.source.getDamageType());
		}
	}
	
	/*
	 Mobが死んだ際のアイテムドロップのイベント
	*/
	@ForgeSubscribe // イベントを指定するアノテーション
	public void addDropItem(LivingDropsEvent event)
	{
		
		//倒されたEntityLivingのインスタンス
		EntityLiving target = event.entityLiving;
		World         world = target.worldObj;
		
		/*
		 LivingEventのフィールド
		 LivingDropsEventのフィールド
		 public final DamageSource source;
		 public final ArrayList<EntityItem> drops;
		 public final int lootingLevel;
		 public final boolean recentlyHit;
		 public final int specialDropValue;
		*/
		
		if (!world.isRemote)
		{
			//player.addChatMessage(target.getEntityName() + "is dead, drops item");
			if (target instanceof EntityZombie)
			{
				int dropNum = world.rand.nextInt(3);
				if (event.lootingLevel > 0)
				{
					dropNum += world.rand.nextInt(event.lootingLevel + 1);
				}
				
				// どのように死んでもドロップ
				event.drops.add(new EntityItem(world, target.posX, target.posY, target.posZ, new ItemStack(Item.feather, dropNum)));
				
				// プレイヤーによる攻撃で死んだらレアドロップ(弓矢ではドロップしない)
				if (event.source.getDamageType().equals("player"))
				{
					/*
					 specialDropValueが一定の値以下のときにレアドロップするという処理になっている
					 バニラではspecialDropValueが5未満でレアドロップ
					*/
					
					// 鉄装備よりも出やすい
					if (event.specialDropValue > 5 && event.specialDropValue < 30)
					{
						event.drops.add(new EntityItem(world, target.posX, target.posY, target.posZ, new ItemStack(Item.pickaxeSteel, 1)));
					}
					
				}
			}
		}
	}
	
	/*
	 EntityLivingが落ちたときのイベント
	 ジャンプ時にも呼ばれる
	*/
	@ForgeSubscribe
	public void onLivingFell(LivingFallEvent event)
	{
		/*
		 LivingFallEventのフィールド
		 public float distance : 落下距離
		*/
		
		World world = event.entityLiving.worldObj;
		
		if (!world.isRemote)
		{
			FMLLog.info("Called Living Fall Event, Distance %f", event.distance);
		}
	
	}
	
	/*
	 EntityLivingがダメージを受けたときのイベント
	*/
	@ForgeSubscribe
	public void onLivingHurt(LivingHurtEvent event)
	{
		/*
		 LivingHurtEventのフィールド
		 public final DamageSource source : ダメージソース
		 public int ammount               : ダメージ量
		*/
		
		World world = event.entityLiving.worldObj;
		
		if (!world.isRemote)
		{
			FMLLog.info("Called Living Hurt Event, DamageSource %s : DamageAmmount %d", event.source.getDamageType(), event.ammount);
		}
	
	}
}