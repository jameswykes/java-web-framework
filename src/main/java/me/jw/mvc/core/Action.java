package me.jw.mvc.core;

public abstract class Action {
    private byte[] output;

    public Action() {
    }

    protected void setOutput(byte[] output) {
        this.output = output;
    }

    public String getOutputAsString() {
        return new String(output);
    }

    public byte[] getOutput() {
        return output;
    }
    
    public abstract void prepare();
}
