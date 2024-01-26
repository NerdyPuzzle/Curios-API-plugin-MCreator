{
  "replace": "false",
  "values": ["minecraft:air"<#list w.getGElementsOfType("curiosbauble") as bauble><#if bauble.slotType == "${data.getModElement().getName()}">,
  "${modid}:${bauble.getModElement().getRegistryName()}"</#if></#list>]
}