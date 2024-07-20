{
  "replace": "false",
  "values": ["minecraft:air"<#list curiosbaubles as bauble><#if bauble.slotType == "HANDS">, 
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}