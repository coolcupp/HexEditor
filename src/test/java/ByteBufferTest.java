import HexEditor.bytes.ByteBuffer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ByteBufferTest {

    @Test
    public void testConstructorCreatesEmptyBuffer() {
        ByteBuffer byteBuffer = new ByteBuffer();
        // Проверяем, что буфер пуст после создания
        assertTrue(byteBuffer.isEmpty(), "Buffer should be empty after initialization");
    }

    @Test
    public void testCopyBytes() {
        ByteBuffer byteBuffer = new ByteBuffer();
        List<Byte> bytes = Arrays.asList((byte) 1, (byte) 2, (byte) 3);

        byteBuffer.copyBytes(bytes);

        // Проверяем, что буфер содержит правильные данные
        List<Byte> buffer = byteBuffer.getBuffer();
        assertEquals(3, buffer.size(), "Buffer should contain 3 bytes after copying");
        assertEquals((byte) 1, buffer.get(0), "First byte should be 1");
        assertEquals((byte) 2, buffer.get(1), "Second byte should be 2");
        assertEquals((byte) 3, buffer.get(2), "Third byte should be 3");
    }

    @Test
    public void testGetBufferReturnsCopy() {
        ByteBuffer byteBuffer = new ByteBuffer();
        List<Byte> bytes = Arrays.asList((byte) 1, (byte) 2, (byte) 3);
        byteBuffer.copyBytes(bytes);

        List<Byte> bufferCopy = byteBuffer.getBuffer();

        // Проверяем, что возвращаемая копия не является тем же объектом
        assertNotSame(bytes, bufferCopy, "getBuffer should return a copy of the buffer");

        // Проверяем, что копия содержит правильные данные
        assertEquals(3, bufferCopy.size(), "The copied buffer should have 3 bytes");
        assertEquals((byte) 1, bufferCopy.get(0), "First byte in copy should be 1");

        // Изменяем копию и проверяем, что оригинальный буфер не изменился
        bufferCopy.set(0, (byte) 99);
        assertNotEquals((byte) 99, byteBuffer.getBuffer().get(0), "Original buffer should not be modified by changes to the copy");
    }

    @Test
    public void testIsEmptyReturnsCorrectResult() {
        ByteBuffer byteBuffer = new ByteBuffer();

        // Пустой буфер
        assertTrue(byteBuffer.isEmpty(), "Buffer should be empty initially");

        // Копируем байты
        byteBuffer.copyBytes(Arrays.asList((byte) 1, (byte) 2));
        assertFalse(byteBuffer.isEmpty(), "Buffer should not be empty after copying bytes");

        // Очищаем буфер
        byteBuffer.copyBytes(Collections.emptyList());
        assertTrue(byteBuffer.isEmpty(), "Buffer should be empty after clearing");
    }
}
