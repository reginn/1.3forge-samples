package samples.mouseclicksamplemod;

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
	public String getTextureFile()
	{
		// スプライトIDの再割り当てをしたい画像へのパス
		return "/samples/sprites/blocks.png";
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			// GUIを開くメソッド
			entityPlayer.openGui(MouseClickSampleMod.instance, MouseClickSampleMod.guiIdMouseClick, world, x, y, z);
			return true;
		}
		
	}
}