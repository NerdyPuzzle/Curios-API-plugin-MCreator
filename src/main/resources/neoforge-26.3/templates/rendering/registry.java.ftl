package ${package}.init;

public class ${JavaModName}CuriosRenderers {
	public static void registerRenderers(FMLClientSetupEvent event) {
	    <#list curiosbaubles as bauble>
	        <#if bauble.hasModel()>
		        ICurioRenderer.register(${JavaModName}Items.${w.getWorkspace().getModElementByName(bauble.item).getRegistryNameUpper()}.get(), ${bauble.getModElement().getName()}Renderer::new);
	        </#if>
	    </#list>
	}
}