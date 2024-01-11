package com.themaestrocode.aaualms.entity;

public class Book {
    private String book_id;
    private String title;
    private String author;
    private String imagePath;
    private String bookStatus;
    private String shelveNo;
    private String isbn;
    private String publisher;
    private String dateAdded;

    public Book(String book_id, String title, String author, String imagePath, String bookStatus, String shelveNo, String isbn, String publisher, String dateAdded) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.imagePath = imagePath;
        this.bookStatus = bookStatus;
        this.shelveNo = shelveNo;
        this.isbn = isbn;
        this.publisher = publisher;
        this.dateAdded = dateAdded;
    }

    public Book(String book_id, String title, String author, String imagePath, String bookStatus, String shelveNo, String isbn, String publisher) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.imagePath = imagePath;
        this.bookStatus = bookStatus;
        this.shelveNo = shelveNo;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getShelveNo() {
        return shelveNo;
    }

    public void setShelveNo(String shelveNo) {
        this.shelveNo = shelveNo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
