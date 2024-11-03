package HexEditor;

import java.util.ArrayList;
import java.util.List;

public class ByteBuffer {
    private List<Byte> bytes = new ArrayList<>();

    public void clear() {
        bytes.clear();
    }

    public void addBytes(List<Byte> newBytes) {
        clear(); // очищаем старые данные, если хотите хранить только последние
        bytes.addAll(newBytes);
    }

    public List<Byte> getBytes() {
        return new ArrayList<>(bytes);
    }
}
