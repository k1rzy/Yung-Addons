package com.encodey.YungAddons.exceptions;

import com.encodey.YungAddons.utils.Utils;
/**
 * @author k1rzy (encodey)
 */
public class BedrockCageExcep extends RuntimeException {
    public BedrockCageExcep() {
        super(Utils.getExecutor() + " you got bedrock cage.");
    }
}