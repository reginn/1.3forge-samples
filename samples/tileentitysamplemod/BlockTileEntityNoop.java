package samples.tileentitysamplemod;

import net.minecraft.src.*;

// 設置したブロックの向きをTileEntityのNBTに保存するだけ
public class BlockTileEntityNoop extends BlockContainer
{
	public BlockTileEntityNoop(int blockId, int terrainId)
	{
		super(blockId, Material.rock);
		this.blockIndexInTexture = terrainId;
		this.setCreativeTab(CreativeTabs.tabDeco);
	}
	
	@Override
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		TileEntityNoop tileEntityNoop = (TileEntityNoop)world.getBlockTileEntity(x, y, z);
		if (side == 1)
		{
			return  62;
		}
		if (side == 0)
		{
			return  62;
		}
		int facing = tileEntityNoop.getFacing();
		if (side != facing)
		{
			return  45;
		}
		else
		{
			return  44;
		}
	}
	
	@Override
	public int getBlockTextureFromSide(int side)
	{
		if (side == 1)
		{
			return  62;
		}
		else if (side == 0)
		{
			return  62;
		}
		else if (side == 4)
		{
			return  44;
		}
		else
		{
			return  45;
		}
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityNoop();
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}
	
	private void setDefaultDirection(World world, int x, int y, int z)
	{
		TileEntityNoop tileEntityNoop = (TileEntityNoop)world.getBlockTileEntity(x, y, z);
		
		if (!world.isRemote)
		{
			int var5 = world.getBlockId(x, y, z - 1);
			int var6 = world.getBlockId(x, y, z + 1);
			int var7 = world.getBlockId(x - 1, y, z);
			int var8 = world.getBlockId(x + 1, y, z);
			byte var9 = 3;
			
			if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6])
			{
				var9 = 3;
			}
			
			if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5])
			{
				var9 = 2;
			}
			
			if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8])
			{
				var9 = 5;
			}
			
			if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7])
			{
				var9 = 4;
			}
			
			tileEntityNoop.setFacing(var9);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityliving)
	{
		int playerFacing = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		
		byte facing = 0;
		if (playerFacing == 0)
		{
			facing = 2;
		}
		if (playerFacing == 1)
		{
			facing = 5;
		}
		if (playerFacing == 2)
		{
			facing = 3;
		}
		if (playerFacing == 3)
		{
			facing = 4;
		}
		
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity != null && tileEntity instanceof TileEntityNoop)
		{
			((TileEntityNoop)tileEntity).setFacing(facing);
			world.markBlockNeedsUpdate(x, y, z);
		}
	}
	
}