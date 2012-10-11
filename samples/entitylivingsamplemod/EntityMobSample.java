package samples.entitylivingsamplemod;

import net.minecraft.src.*;

// ほとんどゾンビのまま
public class EntityMobSample extends EntityMob
{
	public EntityMobSample(World par1World)
	{
		super(par1World);
		this.texture = "/mob/zombie.png";
		this.moveSpeed = 0.23F;
		this.attackStrength = 4;
		this.getNavigator().setBreakDoors(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIBreakDoor(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
		this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
		this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
		this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
	}
	
	public int getMaxHealth()
	{
		return 40;
	}
	
	public int getTotalArmorValue()
	{
		return 2;
	}
	
	protected boolean isAIEnabled()
	{
		return true;
	}
	
	public void onLivingUpdate()
	{
		// ゾンビとは違い日光で燃えない
		super.onLivingUpdate();
	}
	
	protected String getLivingSound()
	{
		return "mob.zombie";
	}
	
	protected String getHurtSound()
	{
		return "mob.zombiehurt";
	}
	
	protected String getDeathSound()
	{
		return "mob.zombiedeath";
	}
	
	protected int getDropItemId()
	{
		// 倒したときにダイアモンドを落とす
		// 通常は腐った肉(rotten flesh)
		return Item.diamond.shiftedIndex;
	}
	
	public EnumCreatureAttribute getCreatureAttribute()
	{
		// 武器のエンチャントで何が有効になるかを返すEnum
		// UNDEADはSMITEで被ダメージが上昇する
		return EnumCreatureAttribute.UNDEAD;
	}
	
	protected void dropRareDrop(int par1)
	{
		// レアドロップとしてダイア防具を落とす
		// 通常は鉄ツールや鉄インゴット
		switch (this.rand.nextInt(4))
		{
			case 0:
				this.dropItem(Item.helmetDiamond.shiftedIndex, 1);
				break;
			case 1:
				this.dropItem(Item.plateDiamond.shiftedIndex, 1);
				break;
			case 2:
				this.dropItem(Item.legsDiamond.shiftedIndex, 1);
				break;
			case 3:
				this.dropItem(Item.bootsDiamond.shiftedIndex, 1);
		}
	}
}
