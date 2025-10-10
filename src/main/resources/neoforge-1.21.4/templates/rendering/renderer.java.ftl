package ${package}.client.renderer;

public class ${name}Renderer implements ICurioRenderer.HumanoidRender {
	private static final ResourceLocation TEXTURE = ResourceLocation.parse("${modid}:textures/entities/${data.baubleModelTexture}");
	private final HumanoidModel humanoidModel;

	public ${name}Renderer() {
        ${data.baubleModel} model = new ${data.baubleModel}(Minecraft.getInstance().getEntityModels().bakeLayer(${data.baubleModel}.LAYER_LOCATION));
        this.humanoidModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of(
            "head", new ModelPart(Collections.emptyList(), Map.of(
                <#if data.helmetModelPart != "Empty">
                    "head", model.${data.helmetModelPart},
                <#else>
                    "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                </#if>
                "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap())
            )),
            <#if data.bodyModelPart != "Empty">
                "body", model.${data.bodyModelPart},
            <#else>
                "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
            </#if>
            <#if data.armsModelPartL != "Empty">
                "left_arm", model.${data.armsModelPartL},
            <#else>
                "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
            </#if>
            <#if data.armsModelPartR != "Empty">
                "right_arm", model.${data.armsModelPartR},
            <#else>
                "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
            </#if>
            <#if data.leggingsModelPartL != "Empty">
                "left_leg", model.${data.leggingsModelPartL},
            <#else>
                "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
            </#if>
            <#if data.leggingsModelPartR != "Empty">
                "right_leg", model.${data.leggingsModelPartR}
            <#else>
                "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
            </#if>
        )), (Function<ResourceLocation, RenderType>) RenderType::entityTranslucent);
	}

    @Override
    public HumanoidModel<? extends HumanoidRenderState> getModel(ItemStack stack, SlotContext slotContext) {
        return this.humanoidModel;
    }

    @Override
    public ResourceLocation getModelTexture(ItemStack stack, SlotContext slotContext) {
        return TEXTURE;
    }
}