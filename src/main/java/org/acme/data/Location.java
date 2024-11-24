package org.acme.data;

/**
 * POJO representation of a search token match location
 * @param lineOffset sequential position of a line in file
 * @param charOffset start of a matched token position
 */
public record Location(int lineOffset, int charOffset) {
    @Override
    public String toString() {
        return String.format("[lineOffset=%d, charOffset=%d]", lineOffset, charOffset);
    }
}
