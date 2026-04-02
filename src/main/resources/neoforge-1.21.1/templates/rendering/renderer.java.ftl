package ${package}.client.renderer;

import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.SlotContext;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;

import ${package}.client.model.${data.baubleModel};

import java.util.Map;
import java.util.Collections;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class ${name}Renderer implements ICurioRenderer {
	private static final ResourceLocation TEXTURE = ResourceLocation.parse("${modid}:textures/entities/${data.baubleModelTexture}");
	private final HumanoidModel humanoidModel;

	public ${name}Renderer() {
        ${data.baubleModel} model = new ${data.baubleModel}(Minecraft.getInstance().getEntityModels().bakeLayer(${data.baubleModel}.LAYER_LOCATION));
        this.humanoidModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of(
            "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
            <#if data.helmetModelPart != "Empty">
               "head", model.${data.helmetModelPart},
            <#else>
               "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
            </#if>
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
        )));
	}

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
		SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
	    int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
	    LivingEntity entity = slotContext.entity();
	    ICurioRenderer.followHeadRotations(entity, this.humanoidModel.head);
	    ICurioRenderer.followBodyRotations(entity, this.humanoidModel);
	    this.humanoidModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
	    VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(TEXTURE), stack.hasFoil());
	    this.humanoidModel.renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY);
  }

}