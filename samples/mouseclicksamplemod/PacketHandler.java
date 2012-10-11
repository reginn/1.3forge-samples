package samples.mouseclicksamplemod;

import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.minecraft.src.*;

import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.network.IPacketHandler;

public class PacketHandler implements IPacketHandler
{
	/*
	 IPacketHandlerの実装
	 このメソッドが読み込むパケットは
	 TileEntity.getAuxillaryInfoPacket(packet)によるパケットと
	 PacketDispatcherによるパケット
	*/
	@Override
	public void onPacketData(NetworkManager network, Packet250CustomPayload packet, Player player)
	{
	
		// @NetworkMod, ならびにgetPacketで設定したチャンネル
		if (packet.channel.equals("MouseClick"))
		{
			// コンテナで読み込む
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			
			// うにゅほ氏のCustomRenderBlockを参考にコンテナへ参照
			Container container = ((EntityPlayerMP)player).craftingInventory;
			if(container != null && container instanceof ContainerMouseClick)
			{
				((ContainerMouseClick)container).readPacketData(data);
			}
		}
		
	}
	
	// パケットを生成するメソッド
	public static Packet getPacket(ContainerMouseClick containerMouseClick)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		// コンテナで書き込む
		containerMouseClick.writePacketData(dos);
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "MouseClick"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;
		
		return packet;
	}
	
}