{
  "replace": "false",
  "values": ["minecraft:air"<#list curiosbaubles as bauble><#if bauble.slotType == "BELT">, 
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}