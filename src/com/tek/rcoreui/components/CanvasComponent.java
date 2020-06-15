package com.tek.rcoreui.components;

import org.bukkit.inventory.ItemStack;

import com.tek.rcoreui.InterfaceComponent;
import com.tek.rcoreui.InterfaceState;
import com.tek.rcoreui.WrappedProperty;

public class CanvasComponent extends InterfaceComponent {

	private WrappedProperty<ItemStack[][]> canvas;
	
	public CanvasComponent(int x, int y, int width, int height) {
		super(x, y, width, height);
		canvas = new WrappedProperty<ItemStack[][]>(new ItemStack[width][height]);
	}
	
	@Override
	public void render(InterfaceState interfaceState, ItemStack[][] drawBuffer) {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				drawBuffer[this.x + x][this.y + y] = canvas.getValue()[x][y];
			}
		}
	}
	
	public WrappedProperty<ItemStack[][]> getCanvas() {
		return canvas;
	}

}
