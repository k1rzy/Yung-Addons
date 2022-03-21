package com.encodey.YungAddons.Sounds;

import javax.management.openmbean.ArrayType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.SimpleType;

/**
 * @author k1rzy (encodey)
 */
public class soundshandler extends ArrayType {

    public soundshandler(SimpleType elementType, boolean primitiveArray) throws OpenDataException {
        super(elementType, primitiveArray);
    }

    public static void ArrayLocation(double arrayString[]) {
        if(arrayString[0] <= 0) return;
        else
            arrayString[0] = 1;
    }
}
