package samples.customrendersamplemod.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import samples.customrendersamplemod.CustomRenderSampleMod;

@SideOnly(Side.CLIENT)
public class SampleBlockRenderingHandler implements ISimpleBlockRenderingHandler
{
	// BaseMod.renderInvBlockに相当
	public void renderInventoryBlock(Block block, int metadata, int renderType, RenderBlocks renderer)
	{
		if (renderType == this.getRenderId())
		{
			
			Tessellator tessellator = Tessellator.instance;
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.8F, 1.0F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1F, 0.0F);
			renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1F);
			renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1F, 0.0F, 0.0F);
			renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
			tessellator.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	// BaseMod.renderWorldBlockに相当
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int renderType, RenderBlocks renderer)
	{
		if (renderType == this.getRenderId())
		{
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.8F, 1.0F);
			renderer.renderStandardBlock(block, x, y, z);
			
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			return false;
		}
		return false;
	}
	
	public boolean shouldRender3DInInventory()
	{
		// インベントリ内の描画を3Dにするか2Dにするか
		// falseにするとブロックのテクスチャのみで描画
		return true;
	}
	
	public int getRenderId()
	{
		return CustomRenderSampleMod.blockNoopRenderType;
	}
}