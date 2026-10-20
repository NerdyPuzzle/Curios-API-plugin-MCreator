if (${input$entity} instanceof Player player${cbi}) {
    ResourceHandler<ItemResource> inventory${cbi} = ${JavaModName}.CuriosApiHelper.getCuriosInventory(player${cbi});
    if (inventory${cbi} != null) {
        for (int i = 0; i < inventory${cbi}.size(); i++) {
            ItemStack itemstackiterator = ItemUtil.getStack(inventory${cbi}, i);
            ${statement$foreach}
        }
    }
}