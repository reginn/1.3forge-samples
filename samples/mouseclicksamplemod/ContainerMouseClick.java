package samples.mouseclicksamplemod;

import java.util.List;
import java.util.ArrayList;

import java.io.DataOutputStream;
import java.io.IOException;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;

import net.minecraft.src.*;

public class ContainerMouseClick extends Container
{
	private EntityPlayer player;                            // プレイヤー(今回はほとんど使わないが, チャット欄にメッセージを出したいときなどに使う
	private IInventory playerInventory;                     // プレイヤーのインベントリ
	private World world;                                    // ワールド
	private int xCoord;                                     // ブロックのX座標
	private int yCoord;                                     // ブロックのY座標
	private int zCoord;                                     // ブロックのZ座標
	private byte clickedPosition = -1;
	
	// コンストラクタ
	public ContainerMouseClick(EntityPlayer player, World world, int x, int y, int z)
	{
		this.player          = player;
		this.playerInventory = player.inventory;
		this.world           = world;
		this.xCoord          = x;
		this.yCoord          = y;
		this.zCoord          = z;
		
		// 各スロットとインベントリを対応させる
		
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
		return this.world.getBlockId(this.xCoord, this.yCoord, this.zCoord) != MouseClickSampleMod.blockNoop.blockID ? false 
			: entityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	// サーバー側ではonButtonPushedが呼ばれないので, 毎tick呼ばれるupdateCraftingResultでボタンの処理を行う
	@Override
	public void updateCraftingResults()
	{
		super.updateCraftingResults();
		
		// 受け取った値からチャット欄にメッセージを表示
		if (clickedPosition > -1)
		{
			if (clickedPosition == 0)
			{
				player.addChatMessage("(0, 0) Area Clicked");
				this.clickedPosition = -1;
			}
			
			if (clickedPosition == 1)
			{
				player.addChatMessage("(0, 1) Area Clicked");
				this.clickedPosition = -1;
			}
			
			if (clickedPosition == 2)
			{
				player.addChatMessage("(0, 2) Area Clicked");
				this.clickedPosition = -1;
			}
			
			if (clickedPosition == 3)
			{
				player.addChatMessage("(1, 0) Area Clicked");
				this.clickedPosition = -1;
			}
			
			if (clickedPosition == 4)
			{
				player.addChatMessage("(1, 1) Area Clicked");
				this.clickedPosition = -1;
			}
			
			if (clickedPosition == 5)
			{
				player.addChatMessage("(1, 2) Area Clicked");
				this.clickedPosition = -1;
			}
		}
	}
	
	// GUIでマウスがクリックされたときの処理
	public void onMouseClicked(int clickedPosition)
	{
		this.clickedPosition = (byte)clickedPosition;
		
		/*
		 サーバー側ではボタンは押されないため, クライアント側とサーバー側の同期を取るため
		 クライアントからサーバーにボタンIDを送る.
		 このメソッドをコメントアウトすれば, すぐ上でclickedPositionをGUIからの入力に更新していても
		 サーバーとの同期が取れないため期待通りに動かないことが容易に確認できる.
		*/ 
		PacketDispatcher.sendPacketToServer(PacketHandler.getPacket(this));
	}
	
	// パケットの読み込み(パケットの受け取りはPacketHandlerで行う)
	public void readPacketData(ByteArrayDataInput data)
	{
		try
		{
			// byte型の読み込み
			this.clickedPosition = data.readByte();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// パケットの書き込み(パケットの生成自体はPacketHandlerで行う)
	public void writePacketData(DataOutputStream dos)
	{
		try
		{
			// byte型(1byte = 8bit = -127～128の整数値)を書き込む
			dos.writeByte(this.clickedPosition);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}