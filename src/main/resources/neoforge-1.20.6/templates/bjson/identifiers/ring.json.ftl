{
  "replace": "false",
  "values": ["minecraft:air"<#list curiosbaubles as bauble><#if bauble.slotType == "RING">, 
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}