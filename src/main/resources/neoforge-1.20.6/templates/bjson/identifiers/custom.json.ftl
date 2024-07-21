{
  "replace": "false",
  "values": [
    <#list w.getGElementsOfType("curiosbauble") as bauble>
      <#if bauble.slotType == "${data.getModElement().getName()}">
        "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
      </#if>
    </#list>
  ]
}