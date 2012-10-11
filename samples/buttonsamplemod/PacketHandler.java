package samples.buttonsamplemod;

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
		if (packet.channel.equals("Button"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			
			// うにゅほ氏のCustomRenderBlockを参考にコンテナへ参照
			Container container = ((EntityPlayerMP)player).craftingInventory;
			if(container != null && container instanceof ContainerButtonNoop)
			{
				((ContainerButtonNoop)container).readPacketData(data);
			}
		}
	}
	
	// パケットを生成するメソッド
	public static Packet getPacket(ContainerButtonNoop containerButtonNoop)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		containerButtonNoop.writePacketData(dos);
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Button"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;
		
		return packet;
	}
	
}