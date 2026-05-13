package net.intelryzen.ultimatequest.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class BedrockBreakerItem extends PickaxeItem {
    public BedrockBreakerItem() {
        super(new BedrockBreakerItemTier(), new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();

        if (level.getBlockState(blockPos).is(Blocks.BEDROCK)) {
            if (!level.isClientSide) {
                level.destroyBlock(blockPos, false);

                context.getItemInHand().hurtAndBreak(1250, context.getPlayer(),
                        context.getHand() == InteractionHand.MAIN_HAND ?
                                EquipmentSlot.MAINHAND :
                                EquipmentSlot.OFFHAND);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private static class BedrockBreakerItemTier implements Tier {

        @Override
        public int getUses() {
            return 5000;
        }

        @Override
        public float getSpeed() {
            return 30.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {

            return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }
    }
}
