package samples.buttonsamplemod;

import java.util.List;
import java.util.ArrayList;

import java.io.DataOutputStream;
import java.io.IOException;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;

import net.minecraft.src.*;

public class ContainerButtonNoop extends Container
{
	private EntityPlayer player;                            // プレイヤー(今回はほとんど使わないが, チャット欄にメッセージを出したいときなどに使う
	private IInventory playerInventory;                     // プレイヤーのインベントリ
	private World world;                                    // ワールド
	private int xCoord;                                     // ブロックのX座標
	private int yCoord;                                     // ブロックのY座標
	private int zCoord;                                     // ブロックのZ座標
	private int buttonId = -1;                              // ボタンID
	
	// コンストラクタ
	public ContainerButtonNoop(EntityPlayer player, World world, int x, int y, int z)
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
		return this.world.getBlockId(this.xCoord, this.yCoord, this.zCoord) != ButtonSampleMod.blockButtonNoop.blockID ? false 
			: entityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	// サーバー側ではonButtonPushedが呼ばれないので, 毎tick呼ばれるupdateCraftingResultでボタンの処理を行う
	@Override
	public void updateCraftingResults()
	{
		super.updateCraftingResults();
		
		if (buttonId > -1)
		{
			if (buttonId == 0)
			{
				player.addChatMessage("Button 0 pushed");
				this.buttonId = -1;
			}
			
			if (buttonId == 1)
			{
				player.addChatMessage("Button 1 pushed");
				this.buttonId = -1;
			}
			
			if (buttonId == 2)
			{
				player.addChatMessage("Button 2 pushed");
				this.buttonId = -1;
			}
			
			if (buttonId == 3)
			{
				player.addChatMessage("Button 3 pushed");
				this.buttonId = -1;
			}
		}
	}
	
	// GUIでボタンが押されたときの処理
	public void onButtonPushed(int buttonId)
	{
		this.buttonId = (byte)buttonId;
		
		/*
		 サーバー側ではボタンは押されないため, クライアント側とサーバー側の同期を取るため
		 クライアントからサーバーにボタンIDを送る.
		 このメソッドをコメントアウトすれば, すぐ上でbuttonIdをGUIからの入力に更新していても
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
			this.buttonId = data.readByte();
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
			dos.writeByte(this.buttonId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}