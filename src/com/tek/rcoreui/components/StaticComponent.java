package com.tek.rcoreui.components;

import org.bukkit.inventory.ItemStack;

import com.tek.rcoreui.InterfaceComponent;
import com.tek.rcoreui.InterfaceState;
import com.tek.rcoreui.InventoryUtils;

public class StaticComponent extends InterfaceComponent {

	private ItemStack item;
	
	public StaticComponent(int x, int y, ItemStack item) {
		this(x, y, 1, 1, item);
	}
	
	public StaticComponent(int x, int y, int width, int height, ItemStack item) {
		super(x, y, width, height);
		this.item = item;
	}
	
	@Override
	public void render(InterfaceState interfaceState, ItemStack[][] drawBuffer) {
		InventoryUtils.drawFilledRectangle(drawBuffer, item, x, y, width, height);
	}

}
