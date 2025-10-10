<#assign values = []>
<#list curiosbaubles as bauble>
    <#if bauble.slotType == var_slot>
        <#assign values += [modid + ":" + w.getWorkspace().getModElementByName(bauble.item).getRegistryName()]>
    </#if>
</#list>
{
  "replace": false,
  "values": [
    <#list values as value>
        "${value}"<#sep>,
    </#list>
  ]
}