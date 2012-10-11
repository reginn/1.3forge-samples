package samples.mouseclicksamplemod.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import samples.mouseclicksamplemod.ContainerMouseClick;

@SideOnly(Side.CLIENT)
public class GuiMouseClick extends GuiContainer
{
	private World               world;
	private ContainerMouseClick containerMouseClick;
	
	public GuiMouseClick(EntityPlayer player, World world, int x, int y, int z)
	{
		super(new ContainerMouseClick(player, world, x, y, z));
		this.containerMouseClick = (ContainerMouseClick)inventorySlots;
		this.world               = world;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		int i = width  - xSize >> 1;
		int j = height - ySize >> 1;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer()
	{
		fontRenderer.drawString("MouseClick Sample", 42, 6, 0x404040);
		fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int k = mc.renderEngine.getTexture("/samples/sprites/GUI_button.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(k);
		int l = width - xSize >> 1;
		int i1 = height - ySize >> 1;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
		
		int black = mc.renderEngine.getTexture("/title/black.png");
		mc.renderEngine.bindTexture(black);
		
		// 適当に2 * 3個の四角形を描画
		drawTexturedModalRect(l + 79 - 18, i1 + 28, 0, 0, 16, 16);
		drawTexturedModalRect(l + 79 - 18, i1 + 46, 0, 0, 16, 16);
		
		drawTexturedModalRect(l + 79, i1 + 28, 0, 0, 16, 16);
		drawTexturedModalRect(l + 79, i1 + 46, 0, 0, 16, 16);
		
		drawTexturedModalRect(l + 79 + 18, i1 + 28, 0, 0, 16, 16);
		drawTexturedModalRect(l + 79 + 18, i1 + 46, 0, 0, 16, 16);
	}
	
	// GUI上でマウスがクリックされたときの処理
	@Override
	protected void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		int x = i - (width  - xSize) / 2;
		int y = j - (height - ySize) / 2;
		int clickedPosition = -1;
		
		// drawGuiContainerBackgroundLayerで描画した黒い四角形をクリックしたときに遷移する
		if (x > 61 && x < 77 && y > 28 && y < 44)
		{
			clickedPosition = 0;
		}
		if (x > 79 && x < 95 && y > 28 && y < 44)
		{
			clickedPosition = 1;
		}
		if (x > 97 && x < 113 && y > 28 && y < 44)
		{
			clickedPosition = 2;
		}
		
		if (x > 61 && x < 77 && y > 46 && y < 62)
		{
			clickedPosition = 3;
		}
		if (x > 79 && x < 95 && y > 46 && y < 62)
		{
			clickedPosition = 4;
		}
		if (x > 97 && x < 113 && y > 46 && y < 62)
		{
			clickedPosition = 5;
		}
		
		// コンテナに情報を渡す
		containerMouseClick.onMouseClicked(clickedPosition);
		
	}
}