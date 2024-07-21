{
  "replace": "false",
  "values": [
    <#list curiosbaubles as bauble>
      <#if bauble.slotType == "CURIO">
        "${modid}:${bauble.getModElement().getRegistryName()}"<#sep>,
      </#if>
    </#list>
  ]
}