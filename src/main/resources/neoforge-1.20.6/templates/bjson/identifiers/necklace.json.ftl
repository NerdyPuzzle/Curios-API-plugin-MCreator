{
  "replace": false,
  "values": [
    <#list curiosbaubles as bauble>
      <#if bauble.slotType == "NECKLACE">
        "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
      </#if>
    </#list>
  ]
}