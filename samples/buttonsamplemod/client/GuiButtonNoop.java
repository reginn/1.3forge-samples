package samples.buttonsamplemod.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import samples.buttonsamplemod.ContainerButtonNoop;

@SideOnly(Side.CLIENT)
public class GuiButtonNoop extends GuiContainer
{
	private World               world;
	private ContainerButtonNoop containerButtonNoop;
	
	public GuiButtonNoop(EntityPlayer player, World world, int x, int y, int z)
	{
		super(new ContainerButtonNoop(player, world, x, y, z));
		this.containerButtonNoop = (ContainerButtonNoop)inventorySlots;
		this.world               = world;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		int i = width  - xSize >> 1;
		int j = height - ySize >> 1;
		
		// ボタンを追加
		// GuiButton(ボタンID, ボタンの始点X, ボタンの始点Y, ボタンの幅, ボタンの高さ, ボタンに表示する文字列)
		controlList.add(new GuiButton(0, i + 36, j + 16, 48, 20 , "Button 0"));
		controlList.add(new GuiButton(1, i + 36, j + 40, 48, 20 , "Button 1"));
		controlList.add(new GuiButton(2, i + 88, j + 16, 48, 20 , "Button 2"));
		controlList.add(new GuiButton(3, i + 88, j + 40, 48, 20 , "Button 3"));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer()
	{
		fontRenderer.drawString("Button Sample", 58, 6, 0x404040);
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
	}
	
	// ボタンが押されたときの処理
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (!par1GuiButton.enabled)
		{
			return;
		}
		
		// どのボタンが押されたかをコンテナに渡す
		containerButtonNoop.onButtonPushed(par1GuiButton.id);
	}
}