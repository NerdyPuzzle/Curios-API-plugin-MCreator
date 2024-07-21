{
  "replace": false,
  "values": [
    <#list curiosbaubles as bauble>
      <#if bauble.slotType == "BODY">
        "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
      </#if>
    </#list>
  ]
}