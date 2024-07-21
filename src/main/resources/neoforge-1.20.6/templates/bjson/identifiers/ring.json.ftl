{
  "replace": false,
  "values": [
    <#list curiosbaubles as bauble>
      <#if bauble.slotType == "RING">
        "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
      </#if>
    </#list>
  ]
}