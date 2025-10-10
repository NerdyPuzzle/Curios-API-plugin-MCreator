<#assign defaultSlots = ["HEAD", "NECKLACE", "BACK", "BODY", "BRACELET", "HANDS", "RING", "BELT", "CHARM", "CURIO"]>
<#assign usedSlots = []>
<#list curiosbaubles as bauble>
    <#if bauble.addSlot>
        <#if defaultSlots?seq_contains(bauble.slotType) && !usedSlots?seq_contains(bauble.slotType)>
            <#assign usedSlots += [bauble.slotType]>
        </#if>
    </#if>
</#list>
{
  "replace": false,
  "entities": [
    "player"
  ],
  "slots": [
    <#list usedSlots as slot>
        "${slot?lower_case}"<#sep>,
    </#list>
  ]
}