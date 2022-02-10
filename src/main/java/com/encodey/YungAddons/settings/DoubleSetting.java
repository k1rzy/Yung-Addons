package com.encodey.YungAddons.settings;


public class DoubleSetting
{
    private Double current;
    private Double min;
    private Double max;

    public DoubleSetting(final Double current, final Double min, final Double max) {
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Double getCurrent() {
        return this.current;
    }

    public void setCurrent(final Double current) {
        this.current = ((current < this.min) ? this.min : ((current > this.max) ? this.max : current));
    }

    public Double getMin() {
        return this.min;
    }

    public void setMin(final Double min) {
        this.min = min;
    }

    public Double getMax() {
        return this.max;
    }

    public void setMax(final Double max) {
        this.max = max;
    }
}
