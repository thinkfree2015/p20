package com.ming800.core.p.model;

import com.ming800.core.p.model.Document;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "basic_document_content")
public class DocumentContent {
    private String id;
    private Document document;
    private String content;

    public DocumentContent(String id, Document document, String content) {
        this.id = id;
        this.document = document;
        this.content = content;
    }

    public DocumentContent() {
    }

    @Id
    @GenericGenerator(name = "id", strategy = "com.ming800.core.p.model.M8idGenerator")
    @GeneratedValue(generator = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
