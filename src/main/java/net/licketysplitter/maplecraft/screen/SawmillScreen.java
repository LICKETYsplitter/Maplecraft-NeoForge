package net.licketysplitter.maplecraft.screen;

import net.licketysplitter.maplecraft.recipe.custom.SawmillRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SawmillScreen extends AbstractContainerScreen<SawmillMenu> {
    private static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.withDefaultNamespace("container/stonecutter/scroller");
    private static final ResourceLocation SCROLLER_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/stonecutter/scroller_disabled");
    private static final ResourceLocation RECIPE_SELECTED_SPRITE = ResourceLocation.withDefaultNamespace("container/stonecutter/recipe_selected");
    private static final ResourceLocation RECIPE_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace("container/stonecutter/recipe_highlighted");
    private static final ResourceLocation RECIPE_SPRITE = ResourceLocation.withDefaultNamespace("container/stonecutter/recipe");
    private static final ResourceLocation BG_LOCATION = ResourceLocation.withDefaultNamespace("textures/gui/container/stonecutter.png");
    private static final int SCROLLER_WIDTH = 12;
    private static final int SCROLLER_HEIGHT = 15;
    private static final int RECIPES_COLUMNS = 4;
    private static final int RECIPES_ROWS = 3;
    private static final int RECIPES_IMAGE_SIZE_WIDTH = 16;
    private static final int RECIPES_IMAGE_SIZE_HEIGHT = 18;
    private static final int SCROLLER_FULL_HEIGHT = 54;
    private static final int RECIPES_X = 52;
    private static final int RECIPES_Y = 14;
    private float scrollOffs;

    private boolean scrolling;
    private int startIndex;
    private boolean displayRecipes;

    public SawmillScreen(SawmillMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        menu.registerUpdateListener(this::containerChanged);
        this.titleLabelY--;
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BG_LOCATION, i, j, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        int k = (int)(41.0F * this.scrollOffs);
        ResourceLocation resourcelocation = this.isScrollBarActive() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, resourcelocation, i + 119, j + 15 + k, 12, 15);
        int l = this.leftPos + 52;
        int i1 = this.topPos + 14;
        int j1 = this.startIndex + 12;
        this.renderButtons(guiGraphics, mouseX, mouseY, l, i1, j1);
        this.renderRecipes(guiGraphics, l, i1, j1);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
        if (this.displayRecipes) {
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.startIndex + 12;
            SelectableRecipe.SingleInputSet<SawmillRecipe> singleinputset = this.menu.getVisibleRecipes();

            for (int l = this.startIndex; l < k && l < singleinputset.size(); l++) {
                int i1 = l - this.startIndex;
                int j1 = i + i1 % 4 * 16;
                int k1 = j + i1 / 4 * 18 + 2;
                if (x >= j1 && x < j1 + 16 && y >= k1 && y < k1 + 18) {
                    ContextMap contextmap = SlotDisplayContext.fromLevel(this.minecraft.level);
                    SlotDisplay slotdisplay = singleinputset.entries().get(l).recipe().optionDisplay();
                    guiGraphics.setTooltipForNextFrame(this.font, slotdisplay.resolveForFirstStack(contextmap), x, y);
                }
            }
        }
    }

    private void renderButtons(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, int lastVisibleElementIndex) {
        for (int i = this.startIndex; i < lastVisibleElementIndex && i < this.menu.getNumberOfVisibleRecipes(); i++) {
            int j = i - this.startIndex;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int i1 = y + l * 18 + 2;
            ResourceLocation resourcelocation;
            if (i == this.menu.getSelectedRecipeIndex()) {
                resourcelocation = RECIPE_SELECTED_SPRITE;
            } else if (mouseX >= k && mouseY >= i1 && mouseX < k + 16 && mouseY < i1 + 18) {
                resourcelocation = RECIPE_HIGHLIGHTED_SPRITE;
            } else {
                resourcelocation = RECIPE_SPRITE;
            }

            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, resourcelocation, k, i1 - 1, 16, 18);
        }
    }

    private void renderRecipes(GuiGraphics guiGraphics, int x, int y, int startIndex) {
        SelectableRecipe.SingleInputSet<SawmillRecipe> singleinputset = this.menu.getVisibleRecipes();
        ContextMap contextmap = SlotDisplayContext.fromLevel(this.minecraft.level);

        for (int i = this.startIndex; i < startIndex && i < singleinputset.size(); i++) {
            int j = i - this.startIndex;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int i1 = y + l * 18 + 2;
            SlotDisplay slotdisplay = singleinputset.entries().get(i).recipe().optionDisplay();
            guiGraphics.renderItem(slotdisplay.resolveForFirstStack(contextmap), k, i1);
        }
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        if (this.displayRecipes) {
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.startIndex + 12;

            for (int l = this.startIndex; l < k; l++) {
                int i1 = l - this.startIndex;
                double d0 = mouseX - (i + i1 % 4 * 16);
                double d1 = mouseY - (j + i1 / 4 * 18);
                if (d0 >= 0.0 && d1 >= 0.0 && d0 < 16.0 && d1 < 18.0 && this.menu.clickMenuButton(this.minecraft.player, l)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, l);
                    return true;
                }
            }

            i = this.leftPos + 119;
            j = this.topPos + 9;
            if (mouseX >= i && mouseX < i + 12 && mouseY >= j && mouseY < j + 54) {
                this.scrolling = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling && this.isScrollBarActive()) {
            int i = this.topPos + 14;
            int j = i + 54;
            this.scrollOffs = ((float)mouseY - i - 7.5F) / (j - i - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int)(this.scrollOffs * this.getOffscreenRows() + 0.5) * 4;
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (super.mouseScrolled(mouseX, mouseY, scrollX, scrollY)) {
            return true;
        } else {
            if (this.isScrollBarActive()) {
                int i = this.getOffscreenRows();
                float f = (float)scrollY / i;
                this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
                this.startIndex = (int)(this.scrollOffs * i + 0.5) * 4;
            }

            return true;
        }
    }

    private boolean isScrollBarActive() {
        return this.displayRecipes && this.menu.getNumberOfVisibleRecipes() > 12;
    }

    protected int getOffscreenRows() {
        return (this.menu.getNumberOfVisibleRecipes() + 4 - 1) / 4 - 3;
    }

    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }
    }
}