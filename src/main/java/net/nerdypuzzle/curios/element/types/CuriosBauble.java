package net.nerdypuzzle.curios.element.types;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.element.parts.TabEntry;
import net.mcreator.element.parts.procedure.Procedure;
import net.mcreator.element.types.interfaces.IItem;
import net.mcreator.element.types.interfaces.IItemWithModel;
import net.mcreator.element.types.interfaces.IItemWithTexture;
import net.mcreator.element.types.interfaces.ITabContainedElement;
import net.mcreator.minecraft.MCItem;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.image.ImageUtils;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.TexturedModel;
import net.mcreator.workspace.resources.Model.Type;

public class CuriosBauble extends GeneratableElement implements IItem, IItemWithModel, ITabContainedElement, IItemWithTexture {
    public int renderType;
    public String texture;
    public String customModelName;
    public String name;
    public String rarity;
    public String slotType;
    public String baubleModel;
    public String baubleModelTexture;
    public TabEntry creativeTab;
    public int stackSize;
    public int enchantability;
    public int useDuration;
    public double toolType;
    public int damageCount;
    public MItemBlock recipeRemainder;
    public boolean destroyAnyBlock;
    public boolean immuneToFire;
    public boolean stayInGridWhenCrafting;
    public boolean damageOnCrafting;
    public boolean enableMeleeDamage;
    public boolean addSlot;
    public double damageVsEntity;
    public List<String> specialInfo;
    public boolean hasGlow;
    public Procedure glowCondition;
    public String guiBoundTo;
    public int inventorySize;
    public int inventoryStackSize;
    public int slotAmount;
    public Procedure onRightClickedInAir;
    public Procedure onRightClickedOnBlock;
    public Procedure onCrafted;
    public Procedure onEntityHitWith;
    public Procedure onItemInInventoryTick;
    public Procedure onItemInUseTick;
    public Procedure onStoppedUsing;
    public Procedure onEntitySwing;
    public Procedure onDroppedByPlayer;
    public Procedure onFinishUsingItem;
    public Procedure curioTick;
    public Procedure onEquip;
    public Procedure onUnequip;
    public boolean isFood;
    public boolean friendlyPigs;
    public boolean enderMask;
    public boolean rotateModel;
    public boolean translateModel;
    public int nutritionalValue;
    public double saturation;
    public MItemBlock eatResultItem;
    public boolean isMeat;
    public boolean isAlwaysEdible;
    public boolean hasModel;
    public String animation;

    private CuriosBauble() {
        this((ModElement)null);
    }

    public CuriosBauble(ModElement element) {
        super(element);
        this.rarity = "COMMON";
        this.inventorySize = 9;
        this.inventoryStackSize = 64;
        this.saturation = 0.30000001192092896;
        this.animation = "eat";
        this.slotAmount = 1;
        this.slotType = "CURIO";
    }

    @Override public List<MCItem> providedMCItems() {
        return List.of(new MCItem.Custom(this.getModElement(), null, "item"));
    }

    public Model getEntityModel() {
        Model.Type modelType = Type.BUILTIN;
        if (!this.baubleModel.equals("Default")) {
            modelType = Type.JAVA;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.baubleModel, modelType);
    }

    public BufferedImage generateModElementPicture() {
        return ImageUtils.resizeAndCrop(this.getModElement().getFolderManager().getTextureImageIcon(this.texture, TextureType.ITEM).getImage(), 32);
    }

    public Model getItemModel() {
        Model.Type modelType = Type.BUILTIN;
        if (this.renderType == 1) {
            modelType = Type.JSON;
        } else if (this.renderType == 2) {
            modelType = Type.OBJ;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.customModelName, modelType);
    }

    public Map<String, String> getTextureMap() {
        Model model = this.getItemModel();
        return model instanceof TexturedModel && ((TexturedModel)model).getTextureMapping() != null ? ((TexturedModel)model).getTextureMapping().getTextureMap() : null;
    }

    public TabEntry getCreativeTab() {
        return this.creativeTab;
    }

    @Override
    public List<MCItem> getCreativeTabItems() {
        return providedMCItems();
    }

    public String getTexture() {
        return this.texture;
    }

    public boolean hasNormalModel() {
        return this.getItemModel().getType() == Type.BUILTIN && this.getItemModel().getReadableName().equals("Normal");
    }

    public boolean hasToolModel() {
        return this.getItemModel().getType() == Type.BUILTIN && this.getItemModel().getReadableName().equals("Tool");
    }

    public boolean hasInventory() {
        return this.guiBoundTo != null && !this.guiBoundTo.isEmpty() && !this.guiBoundTo.equals("<NONE>");
    }

    public boolean hasNonDefaultAnimation() {
        return this.isFood ? !this.animation.equals("eat") : !this.animation.equals("none");
    }

    public boolean hasEatResultItem() {
        return this.isFood && this.eatResultItem != null && !this.eatResultItem.isEmpty();
    }

    public boolean hasModel() {
        return this.hasModel && !baubleModel.equals("Default");
    }

}
