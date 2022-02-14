package com.encodey.YungAddons.settings;

public class IntegerSetting
{
    private Integer current;
    private Integer min;
    private Integer max;

    public IntegerSetting(final Integer current, final Integer min, final Integer max) {
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Integer getCurrent() {
        return this.current;
    }

    public void setCurrent(final Integer current) {
        this.current = ((current < this.min) ? this.min : ((current > this.max) ? this.max : current));
    }

    public Integer getMin() {
        return this.min;
    }

    public void setMin(final Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return this.max;
    }

    public void setMax(final Integer max) {
        this.max = max;
    }
}

