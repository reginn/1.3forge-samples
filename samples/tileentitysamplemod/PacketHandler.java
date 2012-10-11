package samples.tileentitysamplemod;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
		if (packet.channel.equals("TileEntityNoop"))
		{
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			int x, y, z;
			byte facing;
			try
			{
				x = data.readInt();
				y = data.readInt();
				z = data.readInt();
				/*
				 x, y, zはワールドからTileEntityを取るために必要
				*/
				
				// 同期したい情報自体はこいつ
				facing = data.readByte();
				
				// クライアントのワールドをプロキシから取る
				World world = TileEntitySampleMod.proxy.getClientWorld();
				TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
				
				if (tileEntity instanceof TileEntityNoop)
				{
					TileEntityNoop tileEntityshelf = (TileEntityNoop)tileEntity;
					tileEntityshelf.setFacing(facing);
				}
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	// パケットを生成するメソッド
	public static Packet getPacket(TileEntityNoop tileEntityNoop)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		int x = tileEntityNoop.xCoord;
		int y = tileEntityNoop.yCoord;
		int z = tileEntityNoop.zCoord;
		byte facing = tileEntityNoop.getFacing();
		
		try
		{
			/*
			 x, y, zはonPacketData側で必要な情報
			 それ以降は同期させたい情報
			 なお, Containerを経由する情報(チェストのようなインベントリにアイテムを保存する場合)は
			 自動で同期される.
			 この方法で同期するのはContainerを経由しないNBTのデータ
			 また, TileEntitySpecialRendererに情報を渡したい場合も同様
			 その具体的な例に関してはcpw氏のironchestを参照.
			*/
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeByte(facing);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "TileEntityNoop"; // ここでチャンネルを設定する
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();
		packet.isChunkDataPacket = true;
		
		return packet;
	}
}