package dev.utils.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontUtils {
	
	public static Font getFont(String fontFilePath, int fontSize) {
        try {
            File fontFile = new File(fontFilePath);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
            return font;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null; 
        }
    }
}
