package net.nerdypuzzle.curios.ui.modgui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import net.mcreator.blockly.data.Dependency;
import net.mcreator.element.ModElementType;
import net.mcreator.minecraft.JavaModels;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JEmptyBox;
import net.mcreator.ui.component.SearchableComboBox;
import net.mcreator.ui.component.util.ComboBoxUtil;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.dialogs.TextureImportDialogs;
import net.mcreator.ui.help.HelpUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.laf.renderer.ModelComboBoxRenderer;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.minecraft.SoundSelector;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.procedure.AbstractProcedureSelector;
import net.mcreator.ui.procedure.ProcedureSelector;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.Validator;
import net.mcreator.ui.validation.component.VComboBox;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.util.ListUtils;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.elements.VariableTypeLoader;
import net.mcreator.workspace.resources.Model;
import net.mcreator.workspace.resources.Model.Type;
import net.nerdypuzzle.curios.element.types.CuriosBauble;
import net.nerdypuzzle.curios.element.types.PluginElementTypes;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class CuriosBaubleGUI extends ModElementGUI<CuriosBauble> {
    private final SearchableComboBox<String> item = new SearchableComboBox<>();
    private final SearchableComboBox<String> slotType = new SearchableComboBox<>();
    private final JCheckBox addSlot = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private final JSpinner slotAmount = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    private final JCheckBox enderMask = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private final JCheckBox friendlyPigs = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private final JCheckBox snowWalk = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private final SoundSelector equipSound = new SoundSelector(mcreator);
    private ProcedureSelector equipCondition;
    private ProcedureSelector unequipCondition;

    private final JCheckBox hasModel = L10N.checkbox("elementgui.common.enable", new Object[0]);
    private final Model adefault = new Model.BuiltInModel("Default");
    private final SearchableComboBox<Model> baubleModel = new SearchableComboBox(new Model[]{adefault});
    private final VComboBox<String> baubleModelTexture = new SearchableComboBox();
    private final ValidationGroup page1group = new ValidationGroup();

    private ProcedureSelector curioTick;
    private ProcedureSelector onEquip;
    private ProcedureSelector onUnequip;

    // Humanoid model parts
    private final VComboBox<String> helmetModelPart = new SearchableComboBox<>(new String[]{"Empty"});

    private final VComboBox<String> bodyModelPart = new SearchableComboBox<>(new String[]{"Empty"});
    private final VComboBox<String> armsModelPartL = new SearchableComboBox<>(new String[]{"Empty"});
    private final VComboBox<String> armsModelPartR = new SearchableComboBox<>(new String[]{"Empty"});

    private final VComboBox<String> leggingsModelPartL = new SearchableComboBox<>(new String[]{"Empty"});
    private final VComboBox<String> leggingsModelPartR = new SearchableComboBox<>(new String[]{"Empty"});

    private ActionListener modelListener = null;

    public CuriosBaubleGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        initGUI();
        super.finalizeGUI();
    }

    protected void initGUI() {
        curioTick = new ProcedureSelector(withEntry("curios/curio_tick"), mcreator, L10N.t("elementgui.curiosbauble.curio_tick", new Object[0]), AbstractProcedureSelector.Side.SERVER, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        onEquip = new ProcedureSelector(withEntry("curios/on_equip"), mcreator, L10N.t("elementgui.curiosbauble.on_equip", new Object[0]), AbstractProcedureSelector.Side.SERVER, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        onUnequip = new ProcedureSelector(withEntry("curios/on_unequip"), mcreator, L10N.t("elementgui.curiosbauble.on_unequip", new Object[0]), AbstractProcedureSelector.Side.SERVER, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack"));
        equipCondition = new ProcedureSelector(withEntry("curios/can_equip"), mcreator, L10N.t("elementgui.curiosbauble.can_equip", new Object[0]), AbstractProcedureSelector.Side.SERVER, true, VariableTypeLoader.BuiltInTypes.LOGIC, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack")).makeInline();
        unequipCondition = new ProcedureSelector(withEntry("curios/can_unequip"), mcreator, L10N.t("elementgui.curiosbauble.can_unequip", new Object[0]), AbstractProcedureSelector.Side.SERVER, true, VariableTypeLoader.BuiltInTypes.LOGIC, Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/itemstack:itemstack")).makeInline();

        baubleModelTexture.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXX");
        baubleModelTexture.setRenderer(new WTextureComboBoxRenderer.TypeTextures(mcreator.getWorkspace(), TextureType.ENTITY));
        baubleModel.setPreferredSize(new Dimension(250, 25));
        baubleModel.setRenderer(new ModelComboBoxRenderer());
        ComponentUtils.deriveFont(item, 16);
        ComponentUtils.deriveFont(slotType, 16);
        ComponentUtils.deriveFont(baubleModel, 16);
        ComponentUtils.deriveFont(baubleModelTexture, 16);
        ComponentUtils.deriveFont(helmetModelPart, 16);
        ComponentUtils.deriveFont(bodyModelPart, 16);
        ComponentUtils.deriveFont(leggingsModelPartL, 16);
        ComponentUtils.deriveFont(leggingsModelPartR, 16);
        ComponentUtils.deriveFont(armsModelPartL, 16);
        ComponentUtils.deriveFont(armsModelPartR, 16);

        // Bauble properties page
        JPanel pane1 = new JPanel(new BorderLayout(10, 10));
        JPanel bprops = new JPanel(new GridLayout(8, 2, 10, 10));

        bprops.add(HelpUtils.wrapWithHelpButton(withEntry("curios/item"), L10N.label("elementgui.curiosbauble.item", new Object[0])));
        bprops.add(item);
        bprops.add(HelpUtils.wrapWithHelpButton(withEntry("curios/slot_type"), L10N.label("elementgui.curiosbauble.slot_type", new Object[0])));
        bprops.add(slotType);
        bprops.add(HelpUtils.wrapWithHelpButton(withEntry("curios/add_slots"), L10N.label("elementgui.curiosbauble.add_slots", new Object[0])));
        bprops.add(addSlot);
        bprops.add(HelpUtils.wrapWithHelpButton(withEntry("curios/slot_amount"), L10N.label("elementgui.curiosbauble.slot_amount", new Object[0])));
        bprops.add(slotAmount);
        bprops.add(HelpUtils.wrapWithHelpButton(withEntry("curios/endermask"), L10N.label("elementgui.curiosbauble.endermask", new Object[0])));
        bprops.add(enderMask);
        bprops.add(HelpUtils.wrapWithHelpButton(withEntry("curios/piglins"), L10N.label("elementgui.curiosbauble.piglins", new Object[0])));
        bprops.add(friendlyPigs);
        bprops.add(HelpUtils.wrapWithHelpButton(withEntry("curios/snow_walk"), L10N.label("elementgui.curiosbauble.snow_walk", new Object[0])));
        bprops.add(snowWalk);
        bprops.add(HelpUtils.wrapWithHelpButton(withEntry("curios/equip_sound"), L10N.label("elementgui.curiosbauble.equip_sound", new Object[0])));
        bprops.add(equipSound);

        JPanel propertiesPanel = new JPanel(new BorderLayout(10, 10));
        JPanel conditionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        conditionsPanel.add(new JEmptyBox());
        conditionsPanel.add(new JEmptyBox());
        conditionsPanel.add(equipCondition);
        conditionsPanel.add(unequipCondition);

        propertiesPanel.add("Center", PanelUtils.northAndCenterElement(bprops, conditionsPanel));
        pane1.add("Center", PanelUtils.totalCenterInPanel(propertiesPanel));

        pane1.setOpaque(false);
        bprops.setOpaque(false);
        propertiesPanel.setOpaque(false);
        conditionsPanel.setOpaque(false);
        addSlot.setOpaque(false);
        enderMask.setOpaque(false);
        friendlyPigs.setOpaque(false);
        snowWalk.setOpaque(false);
        slotAmount.setEnabled(false);

        slotType.addActionListener((e) -> {
            if (slotType.getSelectedItem() != null)
                addSlot.setEnabled(slotType.getSelectedItem().equals("HEAD") || slotType.getSelectedItem().equals("NECKLACE") || slotType.getSelectedItem().equals("BACK") || slotType.getSelectedItem().equals("BODY") || slotType.getSelectedItem().equals("BRACELET") || slotType.getSelectedItem().equals("HANDS") || slotType.getSelectedItem().equals("RING") || slotType.getSelectedItem().equals("BELT") || slotType.getSelectedItem().equals("CHARM") || slotType.getSelectedItem().equals("CURIO"));
            slotAmount.setEnabled(addSlot.isSelected() && addSlot.isEnabled());
        });

        addSlot.addActionListener((e) -> {
            slotAmount.setEnabled(addSlot.isSelected());
        });

        // Bauble rendering page
        JPanel pane2 = new JPanel(new BorderLayout(10, 10));
        JPanel model = new JPanel(new GridLayout(3, 2, 10, 10));

        JButton importmobtexture = new JButton(UIRES.get("18px.add"));
        importmobtexture.setToolTipText(L10N.t("elementgui.ranged_item.bullet_model_tooltip", new Object[0]));
        importmobtexture.setOpaque(false);
        importmobtexture.addActionListener((e) -> {
            TextureImportDialogs.importMultipleTextures(mcreator, TextureType.ENTITY);
            baubleModelTexture.removeAllItems();
            baubleModelTexture.addItem("");
            List<File> textures = mcreator.getFolderManager().getTexturesList(TextureType.ENTITY);
            Iterator var3 = textures.iterator();

            while(var3.hasNext()) {
                File element = (File)var3.next();
                if (element.getName().endsWith(".png")) {
                    baubleModelTexture.addItem(element.getName());
                }
            }

        });

        model.add(HelpUtils.wrapWithHelpButton(withEntry("curios/model"), L10N.label("elementgui.curiosbauble.has_model", new Object[0])));
        model.add(hasModel);
        model.add(HelpUtils.wrapWithHelpButton(withEntry("curios/java_model"), L10N.label("elementgui.curiosbauble.java_model", new Object[0])));
        model.add(baubleModel);
        model.add(HelpUtils.wrapWithHelpButton(withEntry("curios/model_texture"), L10N.label("elementgui.curiosbauble.model_texture", new Object[0])));
        model.add(PanelUtils.join(FlowLayout.LEFT, baubleModelTexture, importmobtexture));

        model.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1),
                L10N.t("elementgui.curiosbauble.model"), TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, getFont(), Theme.current().getForegroundColor()));

        JPanel renderingPanel = new JPanel(new BorderLayout(10, 10));
        JPanel humanoidParts = new JPanel(new GridLayout(4, 1, 2, 2));

        humanoidParts.add(PanelUtils.westAndCenterElement(L10N.label("elementgui.curiosbauble.head"), helmetModelPart, 5, 5));
        humanoidParts.add(PanelUtils.westAndCenterElement(L10N.label("elementgui.curiosbauble.body"), bodyModelPart, 5, 5));
        humanoidParts.add(PanelUtils.gridElements(1, 2, 2, 2,
                PanelUtils.westAndCenterElement(L10N.label("elementgui.armor.part_arm_left"), armsModelPartL, 5, 5),
                PanelUtils.westAndCenterElement(L10N.label("elementgui.armor.part_arm_right"), armsModelPartR, 5, 5)));
        humanoidParts.add(PanelUtils.gridElements(1, 2, 2, 2,
                PanelUtils.westAndCenterElement(L10N.label("elementgui.armor.part_leg_left"), leggingsModelPartL, 5, 5),
                PanelUtils.westAndCenterElement(L10N.label("elementgui.armor.part_leg_right"), leggingsModelPartR, 5, 5)));

        humanoidParts.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1),
                L10N.t("elementgui.curiosbauble.body_parts"), TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, getFont(), Theme.current().getForegroundColor()));

        renderingPanel.add("Center", PanelUtils.northAndCenterElement(model, humanoidParts));
        pane2.add("Center", PanelUtils.totalCenterInPanel(renderingPanel));

        pane2.setOpaque(false);
        model.setOpaque(false);
        humanoidParts.setOpaque(false);
        renderingPanel.setOpaque(false);
        hasModel.setOpaque(false);

        toggleModel(false);

        hasModel.addActionListener((e) -> {
            toggleModel(hasModel.isSelected());
        });

        baubleModelTexture.setValidator(() -> {
            return adefault.equals(baubleModel.getSelectedItem()) || baubleModelTexture.getSelectedItem() != null && !(baubleModelTexture.getSelectedItem()).isEmpty() ? Validator.ValidationResult.PASSED : new Validator.ValidationResult(Validator.ValidationResultType.ERROR, L10N.t("elementgui.ranged_item.error_custom_model_needs_texture", new Object[0]));
        });
        page1group.addValidationElement(baubleModelTexture);

        modelListener = actionEvent -> {
            Model baublemodel = baubleModel.getSelectedItem();
            if (baublemodel != null && baublemodel != adefault) {
                helmetModelPart.removeAllItems();
                bodyModelPart.removeAllItems();
                armsModelPartL.removeAllItems();
                armsModelPartR.removeAllItems();
                leggingsModelPartL.removeAllItems();
                leggingsModelPartR.removeAllItems();
                reloadPartList(helmetModelPart);
                reloadPartList(bodyModelPart);
                reloadPartList(armsModelPartL);
                reloadPartList(armsModelPartR);
                reloadPartList(leggingsModelPartL);
                reloadPartList(leggingsModelPartR);
                return;
            }
            helmetModelPart.removeAllItems();
            bodyModelPart.removeAllItems();
            armsModelPartL.removeAllItems();
            armsModelPartR.removeAllItems();
            leggingsModelPartL.removeAllItems();
            leggingsModelPartR.removeAllItems();
            helmetModelPart.addItem("Empty");
            bodyModelPart.addItem("Empty");
            armsModelPartL.addItem("Empty");
            armsModelPartR.addItem("Empty");
            leggingsModelPartL.addItem("Empty");
            leggingsModelPartR.addItem("Empty");
        };
        modelListener.actionPerformed(new ActionEvent("", 0, ""));

        // Bauble triggers page
        JPanel pane3 = new JPanel(new BorderLayout(10, 10));
        JPanel events = new JPanel(new GridLayout(1, 3, 10, 10));

        events.add(curioTick);
        events.add(onEquip);
        events.add(onUnequip);

        pane3.add("Center", PanelUtils.totalCenterInPanel(events));

        pane3.setOpaque(false);
        events.setOpaque(false);

        addPage(L10N.t("elementgui.curiosbauble.properties", new Object[0]), pane1).lazyValidate(this::validatePage);
        addPage(L10N.t("elementgui.curiosbauble.rendering", new Object[0]), pane2).validate(page1group);
        addPage(L10N.t("elementgui.curiosbauble.triggers", new Object[0]), pane3);
    }

    public void reloadDataLists() {
        super.reloadDataLists();
        baubleModel.removeActionListener(modelListener);
        AbstractProcedureSelector.ReloadContext context = AbstractProcedureSelector.ReloadContext.create(mcreator.getWorkspace());
        equipCondition.refreshListKeepSelected(context);
        unequipCondition.refreshListKeepSelected(context);
        curioTick.refreshListKeepSelected(context);
        onEquip.refreshListKeepSelected(context);
        onUnequip.refreshListKeepSelected(context);
        ComboBoxUtil.updateComboBoxContents(item, mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == ModElementType.ITEM;
        }).map(ModElement::getName).collect(Collectors.toList()));
        ComboBoxUtil.updateComboBoxContents(baubleModelTexture, ListUtils.merge(Collections.singleton(""), (Collection)mcreator.getFolderManager().getTexturesList(TextureType.ENTITY).stream().map(File::getName).filter((s) -> {
            return s.endsWith(".png");
        }).collect(Collectors.toList())), "");
        ComboBoxUtil.updateComboBoxContents(baubleModel, ListUtils.merge(Collections.singletonList(adefault), (Collection)Model.getModels(mcreator.getWorkspace()).stream().filter((el) -> {
            return el.getType() == Type.JAVA || el.getType() == Type.MCREATOR;
        }).collect(Collectors.toList())));
        ComboBoxUtil.updateComboBoxContents(slotType, ListUtils.merge(List.of("HEAD", "NECKLACE", "BACK", "BODY", "BRACELET", "HANDS", "RING", "BELT", "CHARM", "CURIO"), (List)mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == PluginElementTypes.CURIOSSLOT;
        }).map(ModElement::getName).collect(Collectors.toList())));
        baubleModel.addActionListener(modelListener);
    }

    private boolean itemIsAlreadyBauble() {
        List<CuriosBauble> baubles = mcreator.getWorkspace().getModElements().stream().filter((var) -> {
            return var.getType() == PluginElementTypes.CURIOSBAUBLE && var != getModElement();
        }).map(bauble -> ((CuriosBauble)bauble.getGeneratableElement())).collect(Collectors.toList());
        if (item.getSelectedItem() != null) {
            for (CuriosBauble bauble : baubles)
                if (bauble.item.equals(item.getSelectedItem()))
                    return true;
        }
        return false;
    }

    protected AggregatedValidationResult validatePage() {
        if (!mcreator.getWorkspaceSettings().getDependencies().contains("curios_api"))
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.curiosbauble.needs_api", new Object[0]));
        if (itemIsAlreadyBauble())
            return new AggregatedValidationResult.FAIL(L10N.t("elementgui.curiosbauble.duplicate", new Object[0]));
        return new AggregatedValidationResult.PASS();
    }

    @Override
    protected void afterGeneratableElementStored() {
        mcreator.getWorkspace().getModElements().stream().forEach((var) -> {
            if (var.getType() == PluginElementTypes.CURIOSSLOT) {
                try {
                    mcreator.getGenerator().generateElement(var.getGeneratableElement(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                var.reinit(mcreator.getWorkspace());
            }
        });
    }

    public void toggleModel(boolean enable) {
        baubleModel.setEnabled(enable);
        baubleModelTexture.setEnabled(enable);
        helmetModelPart.setEnabled(enable);
        bodyModelPart.setEnabled(enable);
        armsModelPartL.setEnabled(enable);
        armsModelPartR.setEnabled(enable);
        leggingsModelPartL.setEnabled(enable);
        leggingsModelPartR.setEnabled(enable);
    }

    public void reloadPartList(VComboBox<String> list) {
        try {
            ComboBoxUtil.updateComboBoxContents(list, ListUtils.merge(Collections.singletonList("Empty"),
                    JavaModels.getModelParts((JavaClassSource) Roaster.parse(baubleModel.getSelectedItem().getFile()))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openInEditingMode(CuriosBauble item) {
        this.item.setSelectedItem(item.item);
        baubleModelTexture.setSelectedItem(item.baubleModelTexture);
        Model model = item.getEntityModel();
        if (model != null && model.getType() != null && model.getReadableName() != null) {
            baubleModel.setSelectedItem(model);
        }
        slotType.setSelectedItem(item.slotType);
        addSlot.setSelected(item.addSlot);
        slotAmount.setValue(item.slotAmount);
        hasModel.setSelected(item.hasModel);
        addSlot.setEnabled(slotType.getSelectedItem().equals("HEAD") || slotType.getSelectedItem().equals("NECKLACE") || slotType.getSelectedItem().equals("BACK") || slotType.getSelectedItem().equals("BODY") || slotType.getSelectedItem().equals("BRACELET") || slotType.getSelectedItem().equals("HANDS") || slotType.getSelectedItem().equals("RING") || slotType.getSelectedItem().equals("BELT") || slotType.getSelectedItem().equals("CHARM") || slotType.getSelectedItem().equals("CURIO"));
        slotAmount.setEnabled(addSlot.isSelected() && addSlot.isEnabled());
        enderMask.setSelected(item.enderMask);
        friendlyPigs.setSelected(item.friendlyPigs);
        snowWalk.setSelected(item.snowWalk);
        equipSound.setSound(item.equipSound);
        equipCondition.setSelectedProcedure(item.equipCondition);
        unequipCondition.setSelectedProcedure(item.unequipCondition);
        curioTick.setSelectedProcedure(item.curioTick);
        onEquip.setSelectedProcedure(item.onEquip);
        onUnequip.setSelectedProcedure(item.onUnequip);

        helmetModelPart.setSelectedItem(item.helmetModelPart);
        bodyModelPart.setSelectedItem(item.bodyModelPart);
        armsModelPartL.setSelectedItem(item.armsModelPartL);
        armsModelPartR.setSelectedItem(item.armsModelPartR);
        leggingsModelPartL.setSelectedItem(item.leggingsModelPartL);
        leggingsModelPartR.setSelectedItem(item.leggingsModelPartR);

        toggleModel(hasModel.isSelected());
    }

    public CuriosBauble getElementFromGUI() {
        CuriosBauble item = new CuriosBauble(modElement);
        item.item = this.item.getSelectedItem();
        item.slotType = (String) slotType.getSelectedItem();
        item.addSlot = addSlot.isSelected();
        item.slotAmount = (int) slotAmount.getValue();
        item.enderMask = enderMask.isSelected();
        item.friendlyPigs = friendlyPigs.isSelected();
        item.snowWalk = snowWalk.isSelected();
        item.equipSound = equipSound.getSound();
        item.equipCondition = equipCondition.getSelectedProcedure();
        item.unequipCondition = unequipCondition.getSelectedProcedure();
        item.baubleModel = ((Model) Objects.requireNonNull((Model) baubleModel.getSelectedItem())).getReadableName();
        item.baubleModelTexture = (String) baubleModelTexture.getSelectedItem();
        item.hasModel = hasModel.isSelected();
        item.curioTick = curioTick.getSelectedProcedure();
        item.onEquip = onEquip.getSelectedProcedure();
        item.onUnequip = onUnequip.getSelectedProcedure();

        item.helmetModelPart = helmetModelPart.getSelectedItem();
        item.bodyModelPart = bodyModelPart.getSelectedItem();
        item.armsModelPartL = armsModelPartL.getSelectedItem();
        item.armsModelPartR = armsModelPartR.getSelectedItem();
        item.leggingsModelPartL = leggingsModelPartL.getSelectedItem();
        item.leggingsModelPartR = leggingsModelPartR.getSelectedItem();
        return item;
    }

    @Override public URI contextURL() throws URISyntaxException {
        return null;
    }
    
}
