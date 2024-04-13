{
  "replace": "false",
  "values": ["minecraft:air"<#list curiosbaubles as bauble><#if bauble.slotType == "CURIO">, 
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}