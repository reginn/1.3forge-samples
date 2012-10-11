package samples.buttonsamplemod;

import net.minecraft.src.*;

public class BlockButtonNoop extends Block
{
	public BlockButtonNoop(int blockId, int terrainId)
	{
		super(blockId, Material.rock);
		this.blockIndexInTexture = terrainId;
		this.setCreativeTab(CreativeTabs.tabDeco); // クリエイティブモードの装飾タブに登録する
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
			entityPlayer.openGui(ButtonSampleMod.instance, ButtonSampleMod.guiIdButtonNoop, world, x, y, z);
			return true;
		}
		
	}
}