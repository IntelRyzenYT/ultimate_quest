package net.intelryzen.ultimatequest.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ProcessorBlockScreen extends AbstractContainerScreen<ProcessorBlockMenu> {

    public static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, "textures/gui/processor/processor_gui.png");

    public static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, "textures/gui/arrow_progress.png");

    public static final ResourceLocation REDSTONE_INDICATOR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, "textures/gui/processor/processor_indicator.png");




    public ProcessorBlockScreen(ProcessorBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderRedstoneIndicator(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(ARROW_TEXTURE, x + 76, y + 35, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    private void renderRedstoneIndicator(GuiGraphics graphics, int x, int y) {
        if (menu.entity.powered) {
            graphics.blit(REDSTONE_INDICATOR_TEXTURE, x + 151, y + 12, 0, 0, 10, 12, 10, 12);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}

