{
  "replace": false,
  "values": [
    <#assign baubles = []>
    <#list w.getGElementsOfType("curiosbauble") as bauble>
      <#if bauble.slotType == "${data.getModElement().getName()}">
        <#assign baubles += [modid + ":" + w.getWorkspace().getModElementByName(bauble.item).getRegistryName()]>
      </#if>
    </#list>
    <#list baubles as bauble>
      "${bauble}"<#sep>,
    </#list>
  ]
}