package com.encodey.YungAddons.settings;
public class LongSetting
{
    private Long current;
    private Long min;
    private Long max;

    public LongSetting(final Long current, final Long min, final Long max) {
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Long getCurrent() {
        return this.current;
    }

    public void setCurrent(final Long current) {
        this.current = ((current < this.min) ? this.min : ((current > this.max) ? this.max : current));
    }

    public Long getMin() {
        return this.min;
    }

    public void setMin(final Long min) {
        this.min = min;
    }

    public Long getMax() {
        return this.max;
    }

    public void setMax(final Long max) {
        this.max = max;
    }
}
