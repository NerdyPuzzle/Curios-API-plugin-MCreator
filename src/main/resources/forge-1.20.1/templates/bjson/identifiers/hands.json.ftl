{
  "replace": false,
  "values": [
    <#list curiosbaubles as bauble>
      <#if bauble.slotType == "HANDS">
        "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
      </#if>
    </#list>
  ]
}