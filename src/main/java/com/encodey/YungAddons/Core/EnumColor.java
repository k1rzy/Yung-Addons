package com.encodey.YungAddons.Core;
/**
 * @author k1rzy (encodey)
 */
public enum EnumColor {
    /*RED(""),
    GREEN(""),
    BLUE(""),
    MAGENTA(""),
    PINK(""),*/
    BLACK(0);
    /*WHITE(""),
    ORANGE(""),
    GOLD(""),
    YELLOW(""),
    GREEN("");*/
    final int colorCode;
    EnumColor(int colorCode) {
        this.colorCode = colorCode;
    }
}
