package net.intelryzen.ultimatequest.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PigDestoyerItem extends Item {
    public PigDestoyerItem() {
        super(new Properties().stacksTo(1));
    }


    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
        Vec3 entityPos = target.position();
        float entityXRot = target.getXRot();
        Level level = target.level();

        if (target instanceof Pig) {
            Cow cow = new Cow(EntityType.COW, level);
            cow.setPos(entityPos);
            cow.setXRot(entityXRot);
            target.discard();
            level.addFreshEntity(cow);
        }
        return InteractionResult.SUCCESS;
    }
}
