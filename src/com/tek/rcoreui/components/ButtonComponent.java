package com.tek.rcoreui.components;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import com.tek.rcoreui.InterfaceComponent;
import com.tek.rcoreui.InterfaceState;
import com.tek.rcoreui.InventoryUtils;
import com.tek.rcoreui.WrappedProperty;
import com.tek.rcoreui.events.ButtonClickEvent;

public class ButtonComponent extends InterfaceComponent {

	private ItemStack item;
	private WrappedProperty<ButtonClickEvent> clicked;
	
	public ButtonComponent(int x, int y, ItemStack item) {
		this(x, y, 1, 1, item);
	}
	
	public ButtonComponent(int x, int y, int width, int height, ItemStack item) {
		super(x, y, width, height);
		this.item = item;
		this.clicked = new WrappedProperty<ButtonClickEvent>();
	}
	
	@Override
	public void render(InterfaceState interfaceState, ItemStack[][] drawBuffer) {
		InventoryUtils.drawFilledRectangle(drawBuffer, item, x, y, width, height);
	}
	
	@Override
	public void onClick(InterfaceState interfaceState, ClickType type, ItemStack item, int x, int y) {
		this.clicked.setValue(new ButtonClickEvent(interfaceState.getOwner(), interfaceState, type, item, x, y));
	}
	
	public WrappedProperty<ButtonClickEvent> getClickedProperty() {
		return clicked;
	}

}
