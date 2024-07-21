{
  "replace": "false",
  "values": [
    <#list curiosbaubles as bauble>
      <#if bauble.slotType == "HEAD">
        "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
      </#if>
    </#list>
  ]
}