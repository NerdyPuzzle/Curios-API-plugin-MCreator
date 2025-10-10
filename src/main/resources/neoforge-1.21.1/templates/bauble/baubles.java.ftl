<#-- @formatter:off -->
<#include "../curiostriggers.ftl">

package ${package}.init;

public class ${JavaModName}CuriosCompat {
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        <#list curiosbaubles as bauble>
            event.registerItem(CuriosCapability.ITEM, (stack, context) -> new ICurio() {
                @Override
                public ItemStack getStack() {
                    return stack;
                }

                <#if bauble.enderMask>
                @Override
                public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan) {
                	return true;
                }
                </#if>

                <#if bauble.friendlyPigs>
               	@Override
                public boolean makesPiglinsNeutral(SlotContext slotContext) {
                	return true;
                }
                </#if>

                <#if bauble.snowWalk>
                @Override
                public boolean canWalkOnPowderedSnow(SlotContext slotContext) {
                	return true;
                }
                </#if>

                <#if bauble.equipSound?has_content && bauble.equipSound.getUnmappedValue()?has_content>
                @Override
                public SoundInfo getEquipSound(SlotContext slotContext) {
                    return new SoundInfo(DeferredHolder.create(Registries.SOUND_EVENT, ResourceLocation.parse("${bauble.equipSound}")).value(), 1, 1);
                }
                </#if>

                <@canEquip bauble.equipCondition/>

                <@canUnequip bauble.unequipCondition/>

                <@curioTick bauble.curioTick/>

                <@onEquip bauble.onEquip/>

                <@onUnequip bauble.onUnequip/>
            }, ${JavaModName}Items.${w.getWorkspace().getModElementByName(bauble.item).getRegistryNameUpper()}.get());
        </#list>
    }
}
<#-- @formatter:on -->