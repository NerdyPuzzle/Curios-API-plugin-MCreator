{
  "replace": "false",
  "values": ["minecraft:air"<#list curiosbaubles as bauble><#if bauble.slotType == "BODY">, 
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}