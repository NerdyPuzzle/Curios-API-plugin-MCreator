{
  "replace": "false",
  "values": ["minecraft:air"<#list curiosbaubles as bauble><#if bauble.slotType == "NECKLACE">, 
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}