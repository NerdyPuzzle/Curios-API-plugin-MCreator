<#assign size = 0>
<#list curiosbaubles as bauble>
    <#if bauble.slotType == var_slot>
        <#assign size += bauble.slotAmount>
    </#if>
</#list>
{
  "size": ${size}
}