package HexEditor;

import java.util.ArrayList;
import java.util.List;

public class ByteBuffer {
    private List<Byte> buffer;

    public ByteBuffer() {
        this.buffer = new ArrayList<>();
    }

    public void copyBytes(List<Byte> bytes) {
        buffer.clear();
        buffer.addAll(bytes);
    }

    public List<Byte> getBuffer() {
        return new ArrayList<>(buffer); // Возвращаем копию буфера
    }

    public void clear() {
        buffer.clear();
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }
}
