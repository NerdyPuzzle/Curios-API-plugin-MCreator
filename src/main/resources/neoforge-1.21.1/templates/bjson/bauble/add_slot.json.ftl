<#assign size = 0>
<#assign disableToggle = false>
<#list curiosbaubles as bauble>
    <#if bauble.addSlot && bauble.slotType == var_slot>
        <#assign size += bauble.slotAmount>
        <#if bauble.disabledToggles?seq_contains(var_slot)>
            <#assign disableToggle = true>
        </#if>
    </#if>
</#list>
{
  "size": ${size}
  <#if disableToggle>,
    "render_toggle": false
  </#if>
}