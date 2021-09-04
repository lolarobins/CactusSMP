package ca.cactusmc.smp;

import java.awt.Color;
import java.util.Random;

public class RandomColour {
	
	Color colour;
	
	public Color getColour() {return colour;}
	public String getHex() {return "#"+Integer.toHexString(colour.getRGB()).substring(2);}
	
	public RandomColour(){
	
		Random random = new Random();
		final float hue = random.nextFloat();
		final float saturation = (random.nextInt(2000) + 5000) / 10000f;
		final float luminance = 0.9f;
	
		final Color color = Color.getHSBColor(hue, saturation, luminance);
		this.colour = color;
	}
}
