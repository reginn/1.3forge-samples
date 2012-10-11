package samples.tileentitysamplemod;

import net.minecraft.src.*;

public class TileEntityNoop extends TileEntity
{
	// 向きだけNBTで保存する
	private byte facing;
	
	public byte getFacing()
	{
		return this.facing;
	}
	
	public void setFacing(byte facing)
	{
		this.facing = facing;
	}
	
	/*
	 TileEntityのNBTはサーバーとクライアントで同期されない
	 (プレイヤーが操作した結果はサーバー側のワールド)
	 そのためNBTの情報はゲーム終了時に消滅する.
	*/
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		facing = nbttagcompound.getByte("facing");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setByte("facing", facing);
	}
	
	/*
	 TileEntityはこのメソッドをオーバーライドすることでパケットを送信できる
	 このメソッドによってTileEntityのNBTをクライアントとサーバーで同期させるための
	 パケット送信が行われる.
	*/
	@Override
	public Packet getAuxillaryInfoPacket()
	{
		return PacketHandler.getPacket(this);
	}
	
}
