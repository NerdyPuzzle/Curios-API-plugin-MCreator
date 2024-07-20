{
  "replace": "false",
  "values": ["minecraft:air"<#list curiosbaubles as bauble><#if bauble.slotType == "BRACELET">, 
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}