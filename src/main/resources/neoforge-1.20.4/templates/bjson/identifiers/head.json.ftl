{
  "replace": "false",
  "values": ["minecraft:air"<#list curiosbaubles as bauble><#if bauble.slotType == "HEAD">, 
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}