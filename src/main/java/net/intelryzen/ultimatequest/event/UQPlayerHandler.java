package net.intelryzen.ultimatequest.event;

import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.intelryzen.ultimatequest.dimension.UQDimensions;
import net.intelryzen.ultimatequest.item.UQItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = UltimateQuestMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class UQPlayerHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        dimensionHandler(player);


    }
    private static boolean hasFullUltraArmor(Player player) {
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

        return !boots.isEmpty() && boots.is(UQItems.ULTRA_BOOTS.get())
                && !leggings.isEmpty() && leggings.is(UQItems.ULTRA_LEGGINGS.get())
                && !chestplate.isEmpty() && chestplate.is(UQItems.ULTRA_CHESTPLATE.get())
                && !helmet.isEmpty() && helmet.is(UQItems.ULTRA_HELMET.get());
    }
    private static void dimensionHandler(Player player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            if (player.position().y() <= -70 && hasFullUltraArmor(player)) {
                ServerLevel voidDimension = serverLevel.getServer().getLevel(UQDimensions.VOID_DIMENSION_LEVEL);
                if (voidDimension != null) {
                    BlockPos searchPos = new BlockPos(0, voidDimension.getMaxBuildHeight() - 1, 0);


                    int safeY = 100;
                    boolean foundGround = false;


                    for (int y = voidDimension.getMaxBuildHeight() - 1; y > voidDimension.getMinBuildHeight(); y--) {
                        BlockPos checkPos = new BlockPos(0, y, 0);
                        if (!voidDimension.getBlockState(checkPos).isAir()) {
                            safeY = y;
                            foundGround = true;
                            break;
                        }
                    }


                    if (!foundGround) {
                        safeY = 100;
                        voidDimension.setBlockAndUpdate(new BlockPos(0, safeY, 0), net.minecraft.world.level.block.Blocks.STONE.defaultBlockState());
                    }

                    // 4. Teleport to Y + 1 (the space above the block)
                    BlockPos destination = new BlockPos(0, safeY + 1, 0);

                    player.changeDimension(new DimensionTransition(
                            voidDimension,
                            destination.getBottomCenter(),
                            Vec3.ZERO,
                            player.getYRot(),
                            player.getXRot(),
                            DimensionTransition.DO_NOTHING
                    ));
                }
            }
        }
    }
}
