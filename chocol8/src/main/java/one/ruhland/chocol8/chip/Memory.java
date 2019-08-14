package one.ruhland.chocol8.chip;

import java.util.Arrays;

public class Memory {

    static final int MEMORY_SIZE = 0x0fff;
    static final int PROGRAM_START = 0x0200;
    static final int FONT_SIZE = 0x0050;
    static final int FONT_START = PROGRAM_START - FONT_SIZE;

    private static final byte[] FONT = {
            (byte) 0xf0, (byte) 0x90, (byte) 0x90, (byte) 0x90, (byte) 0xf0, // 0
            (byte) 0x20, (byte) 0x60, (byte) 0x20, (byte) 0x20, (byte) 0x70, // 1
            (byte) 0xf0, (byte) 0x10, (byte) 0xf0, (byte) 0x80, (byte) 0xf0, // 2
            (byte) 0xf0, (byte) 0x10, (byte) 0xf0, (byte) 0x10, (byte) 0xf0, // 3
            (byte) 0x90, (byte) 0x90, (byte) 0xf0, (byte) 0x10, (byte) 0x10, // 4
            (byte) 0xf0, (byte) 0x80, (byte) 0xf0, (byte) 0x10, (byte) 0xf0, // 5
            (byte) 0xf0, (byte) 0x80, (byte) 0xf0, (byte) 0x90, (byte) 0xf0, // 6
            (byte) 0xf0, (byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x40, // 7
            (byte) 0xf0, (byte) 0x90, (byte) 0xf0, (byte) 0x90, (byte) 0xf0, // 8
            (byte) 0xf0, (byte) 0x90, (byte) 0xf0, (byte) 0x10, (byte) 0xf0, // 9
            (byte) 0xf0, (byte) 0x90, (byte) 0xf0, (byte) 0x90, (byte) 0x90, // A
            (byte) 0xe0, (byte) 0x90, (byte) 0xe0, (byte) 0x90, (byte) 0xe0, // B
            (byte) 0xf0, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0xf0, // C
            (byte) 0xe0, (byte) 0x90, (byte) 0x90, (byte) 0x90, (byte) 0xe0, // D
            (byte) 0xf0, (byte) 0x80, (byte) 0xf0, (byte) 0x80, (byte) 0xf0, // E
            (byte) 0xf0, (byte) 0x80, (byte) 0xf0, (byte) 0x80, (byte) 0x80  // F
    };
    
    private byte[] memory = new byte[MEMORY_SIZE];
    
    Memory() {}

    void reset() {
        Arrays.fill(memory, (byte) 0);
        System.arraycopy(FONT, 0, memory, FONT_START, FONT_SIZE);
    }

    public byte getByte(int address) {
        if(address < 0 || address >= MEMORY_SIZE) {
            throw new IllegalArgumentException(String.format("Invalid address: 0x%x", address));
        }

        return memory[address];
    }

    public void setByte(int address, byte value) {
        if(address < 0 || address >= MEMORY_SIZE) {
            throw new IllegalArgumentException(String.format("Invalid address: 0x%x", address));
        }

        memory[address] = value;
    }

    public void setBytes(int address, byte[] bytes) {
        if(address < 0 || address + bytes.length >= MEMORY_SIZE) {
            throw new IllegalArgumentException(String.format("Invalid address: 0x%x (Size: 0x%x)", address, bytes.length));
        }

        System.arraycopy(bytes, 0, memory, address, bytes.length);
    }
}
