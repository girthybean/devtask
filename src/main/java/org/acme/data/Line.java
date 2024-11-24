package org.acme.data;

/**
 * POJO representation of the line of text
 * @param lineNumber sequential position of a line in file
 * @param line content of a line itself
 */
public record Line(int lineNumber, String line) {
}
