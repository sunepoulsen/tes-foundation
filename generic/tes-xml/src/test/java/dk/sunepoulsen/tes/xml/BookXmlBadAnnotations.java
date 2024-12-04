package dk.sunepoulsen.tes.xml;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.util.Objects;

@XmlRootElement(name = "book")
@XmlType(propOrder = {"id", "name", "date", "bad"})
class BookXmlBadAnnotations {
    private Long id;
    private String name;
    private String author;
    private LocalDate date;

    public Long getId() {
        return id;
    }

    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "title")
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    @XmlTransient
    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookXmlBadAnnotations bookXml = (BookXmlBadAnnotations) o;
        return Objects.equals(id, bookXml.id) && Objects.equals(name, bookXml.name) && Objects.equals(author, bookXml.author) && Objects.equals(date, bookXml.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, date);
    }
}
