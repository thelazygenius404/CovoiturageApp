package views;

import java.awt.Color;

/**
 * Modern color constants following a consistent design system
 * Based on Tailwind CSS color palette for professional appearance
 */
public class ColorConstants {

    // Primary colors (Blue theme)
    public static final Color PRIMARY_COLOR = new Color(59, 130, 246);        // blue-500
    public static final Color PRIMARY_HOVER = new Color(37, 99, 235);         // blue-600
    public static final Color PRIMARY_LIGHT = new Color(239, 246, 255);       // blue-50

    // Secondary colors
    public static final Color SECONDARY_COLOR = new Color(107, 114, 128);     // gray-500
    public static final Color SECONDARY_HOVER = new Color(75, 85, 99);        // gray-600

    // Background colors
    public static final Color BACKGROUND_COLOR = new Color(248, 250, 252);    // slate-50
    public static final Color CARD_BACKGROUND = Color.WHITE;                   // white
    public static final Color HOVER_BACKGROUND = new Color(249, 250, 251);    // gray-50
    public static final Color DISABLED_BACKGROUND = new Color(249, 250, 251); // gray-50

    // Text colors
    public static final Color TEXT_PRIMARY = new Color(55, 65, 81);           // gray-700
    public static final Color TEXT_SECONDARY = new Color(107, 114, 128);      // gray-500
    public static final Color TEXT_MUTED = new Color(156, 163, 175);          // gray-400
    public static final Color TEXT_DISABLED = new Color(156, 163, 175);       // gray-400

    // Border colors
    public static final Color BORDER_COLOR = new Color(209, 213, 219);        // gray-300
    public static final Color BORDER_LIGHT = new Color(229, 231, 235);        // gray-200
    public static final Color BORDER_FOCUS = new Color(59, 130, 246);         // blue-500

    // Status colors
    public static final Color SUCCESS_COLOR = new Color(34, 197, 94);         // green-500
    public static final Color SUCCESS_LIGHT = new Color(240, 253, 244);       // green-50
    public static final Color ERROR_COLOR = new Color(239, 68, 68);           // red-500
    public static final Color ERROR_LIGHT = new Color(254, 242, 242);         // red-50
    public static final Color WARNING_COLOR = new Color(245, 158, 11);        // amber-500
    public static final Color WARNING_LIGHT = new Color(255, 251, 235);       // amber-50
    public static final Color INFO_COLOR = new Color(59, 130, 246);           // blue-500
    public static final Color INFO_LIGHT = new Color(239, 246, 255);          // blue-50

    // Table colors
    public static final Color TABLE_HEADER = new Color(249, 250, 251);        // gray-50
    public static final Color TABLE_ROW_ALTERNATE = new Color(249, 250, 251); // gray-50
    public static final Color TABLE_SELECTION = new Color(239, 246, 255);     // blue-50

    // Shadow colors (for future use with custom painting)
    public static final Color SHADOW_LIGHT = new Color(0, 0, 0, 10);          // Very light shadow
    public static final Color SHADOW_MEDIUM = new Color(0, 0, 0, 25);         // Medium shadow
    public static final Color SHADOW_DARK = new Color(0, 0, 0, 50);           // Dark shadow

    // Special colors
    public static final Color OVERLAY = new Color(0, 0, 0, 128);              // Semi-transparent overlay
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);            // Fully transparent

    // Legacy colors (for backward compatibility)
    @Deprecated
    public static final Color ORANGE_SECONDARY = new Color(255, 87, 34);      // Original secondary color

    /**
     * Utility method to create a color with custom alpha
     * @param baseColor The base color
     * @param alpha Alpha value (0-255)
     * @return Color with specified alpha
     */
    public static Color withAlpha(Color baseColor, int alpha) {
        return new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);
    }

    /**
     * Utility method to darken a color
     * @param color The base color
     * @param factor Factor to darken by (0.0 to 1.0)
     * @return Darkened color
     */
    public static Color darken(Color color, float factor) {
        int red = Math.max(0, (int) (color.getRed() * (1 - factor)));
        int green = Math.max(0, (int) (color.getGreen() * (1 - factor)));
        int blue = Math.max(0, (int) (color.getBlue() * (1 - factor)));
        return new Color(red, green, blue, color.getAlpha());
    }

    /**
     * Utility method to lighten a color
     * @param color The base color
     * @param factor Factor to lighten by (0.0 to 1.0)
     * @return Lightened color
     */
    public static Color lighten(Color color, float factor) {
        int red = Math.min(255, (int) (color.getRed() + (255 - color.getRed()) * factor));
        int green = Math.min(255, (int) (color.getGreen() + (255 - color.getGreen()) * factor));
        int blue = Math.min(255, (int) (color.getBlue() + (255 - color.getBlue()) * factor));
        return new Color(red, green, blue, color.getAlpha());
    }
}