package de.serverlessbuch.lambda.jaxrs.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Niko Köbler, http://www.n-k.de, @dasniko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String author;
    private String title;
}
