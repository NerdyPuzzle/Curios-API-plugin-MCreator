{
  "replace": "false",
  "values": [
    <#list curiosbaubles as bauble>
      <#if bauble.slotType == "BELT">
        "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
      </#if>
    </#list>
  ]
}