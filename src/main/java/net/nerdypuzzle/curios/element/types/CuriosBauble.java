package net.nerdypuzzle.curios.element.types;

import java.util.*;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.Sound;
import net.mcreator.element.parts.procedure.Procedure;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.Model.Type;

public class CuriosBauble extends GeneratableElement {
    public String item;
    public String slotType;
    public int slotAmount;
    public boolean addSlot;
    public boolean enderMask;
    public boolean friendlyPigs;
    public boolean snowWalk;
    public Sound equipSound;
    public Procedure equipCondition;
    public Procedure unequipCondition;

    public boolean hasModel;
    public String baubleModel;
    public String baubleModelTexture;

    public Procedure curioTick;
    public Procedure onEquip;
    public Procedure onUnequip;

    // Humanoid model parts
    public String helmetModelPart;
    public String bodyModelPart;
    public String armsModelPartL;
    public String armsModelPartR;
    public String leggingsModelPartL;
    public String leggingsModelPartR;

    private CuriosBauble() {
        this((ModElement)null);
    }

    public CuriosBauble(ModElement element) {
        super(element);
        this.slotAmount = 1;
        this.slotType = "CURIO";
    }

    public Model getEntityModel() {
        Model.Type modelType = Type.BUILTIN;
        if (!this.baubleModel.equals("Default")) {
            modelType = Type.JAVA;
        }

        return Model.getModelByParams(this.getModElement().getWorkspace(), this.baubleModel, modelType);
    }

    public boolean hasModel() {
        return this.hasModel && !baubleModel.equals("Default");
    }

}
