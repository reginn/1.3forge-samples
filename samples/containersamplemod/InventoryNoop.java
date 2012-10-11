package samples.containersamplemod;

import net.minecraft.src.*;

public class InventoryNoop implements IInventory
{
	private ItemStack[] stackInput = new ItemStack[9];
	
	// IInventoryを実装しただけ
	public int getSizeInventory()
	{
		return stackInput.length;
	}
	
	public ItemStack getStackInSlot(int i)
	{
		return this.stackInput[i];
	}
	
	public String getInvName()
	{
		return "3*3inventory";
	}
	
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.stackInput[par1] != null)
		{
			ItemStack var3 = this.stackInput[par1];
			this.stackInput[par1] = null;
			return var3;
		}
		else
		{
			return null;
		}
	}
	
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.stackInput[par1] != null)
		{
			ItemStack var2 = this.stackInput[par1];
			this.stackInput[par1] = null;
			return var2;
		}
		else
		{
			return null;
		}
	}
	
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.stackInput[par1] = par2ItemStack;
	}
	
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	public void onInventoryChanged()
	{
	}
	
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return true;
	}
	
	public void openChest()
	{
	}
	
	public void closeChest()
	{
	}
	
}