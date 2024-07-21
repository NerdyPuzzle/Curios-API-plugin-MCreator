{
  "replace": "false",
  "values": [
    <#list curiosbaubles as bauble>
      <#if bauble.slotType == "BACK">
        "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
      </#if>
    </#list>
  ]
}