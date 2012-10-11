package samples.containersamplemod;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.src.*;

public class ContainerNoop extends Container
{
	private IInventory inventoryNoop = new InventoryNoop(); // コンテナのインベントリ(3*3)
	private EntityPlayer player;                            // プレイヤー(今回はほとんど使わないが, チャット欄にメッセージを出したいときなどに使う
	private IInventory playerInventory;                     // プレイヤーのインベントリ
	private World world;                                    // ワールド
	private int xCoord;                                     // ブロックのX座標
	private int yCoord;                                     // ブロックのY座標
	private int zCoord;                                     // ブロックのZ座標
	
	// コンストラクタ
	public ContainerNoop(EntityPlayer player, World world, int x, int y, int z)
	{
		this.player          = player;
		this.playerInventory = player.inventory;
		this.world           = world;
		this.xCoord          = x;
		this.yCoord          = y;
		this.zCoord          = z;
		
		// 各スロットとインベントリを対応させる
		
		// 9*9のインベントリ
		for (int rows = 0; rows < 3; ++rows)
		{
			for (int columns = 0; columns < 3; ++columns)
			{
				// Slot(slotが参照するIInventory, スロット番号, スロットの位置(GUI画像の左上から, 横), スロットの位置(縦)
				// GUIの画像を開いてみればわかるが, 62とは画像の左端から62ドット目, 17は画像の上から17ドット目という意味
				addSlotToContainer(new Slot(inventoryNoop, columns + rows * 3,  62 + columns * 18, 17 + rows * 18));
			}
		}
		
		
		// 3*9のプレイヤーインベントリ
		for (int rows = 0; rows < 3; ++rows)
		{
			for (int slotIndex = 0; slotIndex < 9; ++slotIndex)
			{
				addSlotToContainer(new Slot(playerInventory, slotIndex + rows * 9 + 9, 8 + slotIndex * 18, 84 + rows * 18));
			}
		}
		
		// 1*9のプレイヤーインベントリ
		for (int slotIndex = 0; slotIndex < 9; ++slotIndex)
		{
			addSlotToContainer(new Slot(playerInventory, slotIndex, 8 + slotIndex * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{
		// GUIを開きたいブロックかどうかの判定
		return this.world.getBlockId(this.xCoord, this.yCoord, this.zCoord) != ContainerSampleMod.blockNoop.blockID ? false 
			: entityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	// GUIを閉じたときの処理, 今回はワークベンチと同じようにGUIを閉じるとその場にアイテムをドロップする
	@Override
	public void onCraftGuiClosed(EntityPlayer entityPlayer)
	{
		super.onCraftGuiClosed(entityPlayer);
		
		// !world.isRemote, すなわちサーバー側のワールドでのみドロップさせる
		if (!this.world.isRemote)
		{
			List<ItemStack> dropItemList = new ArrayList<ItemStack>(9);
			
			// コンテナ内のインベントリにアイテムがあればドロップリストに追加
			for (int i = 0; i < 9; ++i)
			{
				dropItemList.add(this.inventoryNoop.getStackInSlotOnClosing(i));
			}
			
			// アイテムをドロップ
			for (ItemStack dropItem : dropItemList)
			{
				if (dropItem != null)
				{
					entityPlayer.dropPlayerItem(dropItem);
				}
			}
		}
	}
	
	// シフト＋クリック時の処理
	@Override
	public ItemStack transferStackInSlot(int slotIndex)
	{
		ItemStack var2 = null;
		Slot var3 = (Slot)this.inventorySlots.get(slotIndex);
		
		if (var3 != null && var3.getHasStack())
		{
			ItemStack var4 = var3.getStack();
			var2 = var4.copy();
			
			/*
			 シフト＋クリックしたスロットの番号が9未満, すなわちコンテナのインベントリのときは
			 9～45, すなわちプレイヤーのインベントリにマージする
			*/ 
			if (slotIndex < 9)
			{
				if (!this.mergeItemStack(var4, 9, 45, true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var4, 0, 9, false)) // プレイヤーのインベントリでシフト＋クリックした場合はコンテナのインベントリにマージする
			{
				return null;
			}
			
			// スタックサイズが0ならスロットは空にする
			if (var4.stackSize == 0)
			{
				var3.putStack((ItemStack)null);
			}
			else
			{
				var3.onSlotChanged();
			}
			
			if (var4.stackSize == var2.stackSize)
			{
				return null;
			}
			
			var3.onPickupFromSlot(var4);
		}
		
		return var2;
	}
	
	
}