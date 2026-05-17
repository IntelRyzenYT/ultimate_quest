package net.intelryzen.ultimatequest.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.intelryzen.ultimatequest.UltimateQuestMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class RefinerScreen extends AbstractContainerScreen<RefinerMenu> {
    public static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, "textures/gui/refiner/refiner_gui.png");

    public static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(UltimateQuestMod.MODID, "textures/gui/arrow_progress.png");

    public RefinerScreen(RefinerMenu menu, Inventory playerInventory, Component title) {
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
        renderFluidTank(guiGraphics, x + 147, y + 26, menu.getFluid(), menu.getFluidCapacity());
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(ARROW_TEXTURE, x + 77, y + 52, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    private void renderFluidTank(GuiGraphics guiGraphics, int x, int y, FluidStack fluidStack, int capacity) {
        if (fluidStack.isEmpty()) return;

        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation fluidStill = extensions.getStillTexture(fluidStack);
        if (fluidStill == null) return;

        TextureAtlasSprite sprite = this.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
        int color = extensions.getTintColor(fluidStack);

        int tankHeight = 52;
        int scaledHeight = (int) ((long) fluidStack.getAmount() * tankHeight / capacity);
        if (scaledHeight == 0 && fluidStack.getAmount() > 0) scaledHeight = 1;

        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;
        float a = ((color >> 24) & 0xFF) / 255f;

        guiGraphics.setColor(r, g, b, a);

        int yOffset = y + tankHeight - scaledHeight;
        guiGraphics.blit(x, yOffset, 0, 16, scaledHeight, sprite);

        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (mouseX >= x + 147 && mouseX < x + 165 && mouseY >= y + 28 && mouseY < y + 80) {
            FluidStack fluid = menu.getFluid();
            Component text = fluid.isEmpty()
                    ? Component.literal("Empty")
                    : Component.translatable(fluid.getDescriptionId()).append(": " + fluid.getAmount() + " / " + menu.getFluidCapacity() + " mB");

            guiGraphics.renderComponentTooltip(this.font, List.of(text), mouseX - x, mouseY - y);
        }
    }
}