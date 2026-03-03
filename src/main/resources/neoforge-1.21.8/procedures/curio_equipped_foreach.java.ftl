if (${input$entity} instanceof Player player${cbi}) {
    IItemHandler inventory${cbi} = ${JavaModName}.CuriosApiHelper.getCuriosInventory(player${cbi});
    if (inventory${cbi} != null) {
        for (int i = 0; i < inventory${cbi}.getSlots(); i++) {
            ItemStack itemstackiterator = inventory${cbi}.getStackInSlot(i);
            ${statement$foreach}
        }
    }
}