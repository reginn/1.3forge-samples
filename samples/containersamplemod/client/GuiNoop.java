package samples.containersamplemod.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import samples.containersamplemod.ContainerNoop;

@SideOnly(Side.CLIENT)
public class GuiNoop extends GuiContainer
{
	private World         world;
	private ContainerNoop containerNoop;
	
	public GuiNoop(EntityPlayer player, World world, int x, int y, int z)
	{
		super(new ContainerNoop(player, world, x, y, z));
		this.containerNoop = (ContainerNoop)inventorySlots;
		this.world         = world;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer()
	{
		fontRenderer.drawString("Sample", 60, 6, 0x404040);
		fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int k = mc.renderEngine.getTexture("/gui/trap.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(k);
		int l = width - xSize >> 1;
		int i1 = height - ySize >> 1;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
	}
}