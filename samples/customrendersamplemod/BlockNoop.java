package samples.customrendersamplemod;

import net.minecraft.src.*;

public class BlockNoop extends Block
{
	public BlockNoop(int blockId, int terrainId)
	{
		super(blockId, Material.rock);
		this.blockIndexInTexture = terrainId;
		this.setCreativeTab(CreativeTabs.tabDeco);
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
	public int getRenderType()
	{
		return CustomRenderSampleMod.blockNoopRenderType;
	}
}