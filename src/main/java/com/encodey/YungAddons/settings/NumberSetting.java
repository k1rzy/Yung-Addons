package com.encodey.YungAddons.settings;

import com.google.gson.annotations.*;

public class NumberSetting extends Setting {
    double min;
    double max;
    double increment;
    @Expose
    @SerializedName("value")
    private double value;

    public NumberSetting(final String name, final double defaultValue, final double minimum, final double maximum, final double increment) {
        this.name = name;
        this.value = defaultValue;
        this.min = minimum;
        this.max = maximum;
        this.increment = increment;
    }

    public static double clamp(double value, final double min, final double max) {
        value = Math.max(min, value);
        value = Math.min(max, value);
        return value;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        value = clamp(value, this.min, this.max);
        value = Math.round(value * (1.0 / this.increment)) / (1.0 / this.increment);
        this.value = value;
    }

    public void increment(final boolean positive) {
        if (positive) {
            this.setValue(this.getValue() + this.getIncrement());
        }
        if (!positive) {
            this.setValue(this.getValue() - this.getIncrement());
        }
    }

    public double getMin() {
        return this.min;
    }

    public void setMin(final double min) {
        this.min = min;
    }

    public double getMax() {
        return this.max;
    }

    public void setMax(final double max) {
        this.max = max;
    }

    public double getIncrement() {
        return this.increment;
    }

    public void setIncrement(final double increment) {
        this.increment = increment;
    }
}
