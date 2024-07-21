{
  "replace": false,
  "values": [
    <#list curiosbaubles as bauble>
      <#if bauble.slotType == "BRACELET">
        "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
      </#if>
    </#list>
  ]
}