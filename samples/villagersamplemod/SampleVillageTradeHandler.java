package samples.villagersamplemod;

import java.util.Random;

import net.minecraft.src.*;

// 利用するFMLのAPI
import cpw.mods.fml.common.registry.VillagerRegistry;

// インナーインタフェースを実装
public class SampleVillageTradeHandler implements VillagerRegistry.IVillageTradeHandler
{
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
	{
		/*
		  MerchantRecipeのコンストラクタは以下の3つ
		  MerchantRecipe(ItemStack, ItemStack, ItemStack) : 欲しいもの1, 欲しいもの2, 売り物
		  MerchantRecipe(ItemStack, ItemStack) : 欲しいもの, 売り物
		  MerchantRecipe(ItemStack, Item) : 欲しいもの, 売り物
		*/
		recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald, 10), new ItemStack(Item.diamond, 5), new ItemStack(Item.record11)));
		
		/*
		  複数追加しても, 村人一人の選択肢が増えるわけではない
		  選択肢の増加, 売り物の変更はバニラと同様に行われる
		*/
		
		recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald, 10), new ItemStack(Item.diamond, 5), new ItemStack(Item.record13)));
		recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald, 10), new ItemStack(Item.diamond, 5), new ItemStack(Item.recordCat)));
		
		
	}
}