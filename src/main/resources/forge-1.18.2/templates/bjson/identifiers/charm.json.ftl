{
  "replace": "false",
  "values": ["minecraft:air"<#list curiosbaubles as bauble><#if bauble.slotType == "CHARM">, 
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}