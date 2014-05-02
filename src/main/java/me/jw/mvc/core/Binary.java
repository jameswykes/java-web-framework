package me.jw.mvc.core;

public class Binary extends Action {
    private byte[] data;

    public Binary(byte[] data) {
        this.data = data;
    }

    @Override
    public void prepare() {
        setOutput(data);
    }
}
