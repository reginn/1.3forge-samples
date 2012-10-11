package samples.playereventsamplemod;

import net.minecraft.src.*;

// イベント指定のためのアノテーション
import net.minecraftforge.event.ForgeSubscribe;

// 各イベントクラス
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

public class PlayerEventSampleHooks
{
	/*
	 プレイヤーが他のEntityを攻撃(左クリック)したときのイベント
	*/
	@ForgeSubscribe
	public void onAttackedEntity(AttackEntityEvent event)
	{
		/*
		 public final EntityPlayer entityPlayer : プレイヤーイベント共通, プレイヤーのインスタンス
		 public final Entity target             : 攻撃されたEntity
		*/
		
		EntityPlayer player = event.entityPlayer;
		Entity       target = event.target;
		World         world = event.entityPlayer.worldObj;
		
		if (!world.isRemote)
		{
			player.addChatMessage("Attacked " + target.getEntityName());
		}
	}
	
	/*
	 ブロックに骨粉を使用したときに呼ばれるイベント
	 主にmodで追加した苗木を木にしたり, 小麦のような植物を促成させるときに使う
	*/
	@ForgeSubscribe
	public void onUsedBonemeal(BonemealEvent event)
	{
		/*
		 public final World world : 骨粉が使われたワールド
		 public final int ID      : 骨粉が使われたブロックのID
		 public final int X       : 上記のブロックのX座標
		 public final int Y       : 上記のブロックのY座標
		 public final int Z       : 上記のブロックのZ座標
		*/
		
		World world = event.world;
		int blockID = event.ID;
		int       x = event.X;
		int       y = event.Y;
		int       z = event.Z;
		
		if (!world.isRemote)
		{
			// 右クリックしたブロックのIDを表示
			event.entityPlayer.addChatMessage(String.valueOf(blockID));
			
			// 菌糸ブロックに骨粉を使ったら赤いキノコが生成される
			if (blockID == Block.mycelium.blockID)
			{
				world.setBlockWithNotify(x, y + 1, z, Block.mushroomRed.blockID);
				
				// 無事イベントが終了したら終了フラグを立てておく
				event.setHandeled();
			}
		}
	}
	
	/*
	 Entityを右クリックしたときのイベント
	*/
	@ForgeSubscribe
	public void onInteractEntity(EntityInteractEvent event)
	{
		/*
		 public final EntityPlayer entityPlayer : プレイヤーイベント共通, プレイヤーのインスタンス
		 public final Entity target             : EntityInteractEventのフィールド, 右クリックされたEntityのインスタンス
		*/
		
		EntityPlayer player = event.entityPlayer;
		Entity       target = event.target;
		World         world = event.entityPlayer.worldObj;
		
		if (!world.isRemote)
		{
			// Entity名を表示
			player.addChatMessage("Interacted " + target.getEntityName());
		}
	}
	
	/*
	 EntityItem(ドロップ状態のアイテム)をプレイヤーが取得したときのイベント
	*/
	@ForgeSubscribe
	public void onGetEntityItem(EntityItemPickupEvent event)
	{
		/*
		 public final EntityPlayer entityPlayer : プレイヤーイベント共通, プレイヤーのインスタンス
		 public final EntityItem item           : 取得するアイテム
		*/
		EntityPlayer player = event.entityPlayer;
		EntityItem     item = event.item;
		World         world = event.entityPlayer.worldObj;
		
		if (!world.isRemote)
		{
			// 取得したアイテム名を表示
			player.addChatMessage(item.item.getItemName());
		}
	}
	
	/*
	 プレイヤーが手に持っているアイテムが壊れたとき,
	 もしくはスタックされたアイテムが0になったときに呼ばれるイベント.
	*/
	@ForgeSubscribe // イベントを指定するアノテーション
	public void onDestoryedItem(PlayerDestroyItemEvent event) // 引数にイベントクラスを指定
	{
		/*
		 public final EntityPlayer entityPlayer : プレイヤーイベント共通, プレイヤーのインスタンス
		 public final ItemStack original        : PlayerDestroyItemEventのフィールド, プレイヤーの手に持っているアイテム
		*/
		
		EntityPlayer     player = event.entityPlayer;
		ItemStack destroyedItem = event.original;
		World             world = event.entityPlayer.worldObj;
		
		if (!world.isRemote)
		{
			// アイテム名を表示する
			player.addChatMessage(destroyedItem.getItemName());
		}
	}
	
	/*
	 プレイヤーがベッドで寝たときのイベント
	*/
	@ForgeSubscribe
	public void onSlept(PlayerSleepInBedEvent event)
	{
		/*
		 public final EntityPlayer entityPlayer : プレイヤーイベント共通, プレイヤーのインスタンス
		 public EnumStatus result = null : プレイヤーの状態
		 public final int x              : ベッドのX座標
		 public final int y              : ベッドのY座標
		 public final int z              : ベッドのZ座標
		*/
		
		EntityPlayer player = event.entityPlayer;
		World         world = event.entityPlayer.worldObj;
		EnumStatus   result = event.result;
		
		if (!world.isRemote)
		{
			player.addChatMessage("sleeping");
			
			// 満腹度が9以上のときに眠るとライフが全回復
			int foodLevel = player.getFoodStats().getFoodLevel();
			if (foodLevel > 18)
			{
				player.heal(20);
			}
		}
		
	}
}