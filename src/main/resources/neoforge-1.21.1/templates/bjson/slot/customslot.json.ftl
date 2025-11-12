{
  "replace": false,
  "size": ${data.amount},
  "operation": "SET",
  <#if data.changeOrder>
    "order": ${data.slotOrder},
  </#if>
  "render_toggle": ${!data.modelToggling},
  "icon": "curios:slot/${data.texture?replace(".png", "")}"
}