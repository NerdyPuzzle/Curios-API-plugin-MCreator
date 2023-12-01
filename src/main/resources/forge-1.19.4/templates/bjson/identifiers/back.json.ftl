{
  "replace": "false",
  "values": ["minecraft:air"<#list curiosbaubles as bauble><#if bauble.slotType == "BACK">, 
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}