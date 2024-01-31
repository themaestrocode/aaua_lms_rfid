package com.themaestrocode.aaualms.entity;

import java.sql.Time;
import java.sql.Timestamp;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String imagePath;
    private String bookStatus;
    private String shelveNo;
    private String isbn;
    private String publisher;
    private Timestamp dateAdded;

    //complete constructor for retrieving book
    public Book(String bookId, String title, String author, String imagePath, String bookStatus, String shelveNo, String isbn, String publisher, Timestamp dateAdded) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.imagePath = imagePath;
        this.bookStatus = bookStatus;
        this.shelveNo = shelveNo;
        this.isbn = isbn;
        this.publisher = publisher;
        this.dateAdded = dateAdded;
    }

    //constructor for adding a new book
    public Book(String bookId, String title, String author, String imagePath, String shelveNo, String isbn, String publisher) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.imagePath = imagePath;
        this.shelveNo = shelveNo;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    //Inner class
    public class IssuedBook {
        private int issueId;
        private String bookId;
        private String borrowerLibraryId;
        private String userType;
        private Timestamp issueDate;
        private Timestamp dueDate;

        public IssuedBook(int issueId, String bookId, String borrowerLibraryId, String userType, Timestamp issueDate, Timestamp dueDate) {
            this.issueId = issueId;
            this.bookId = bookId;
            this.borrowerLibraryId = borrowerLibraryId;
            this.userType = userType;
            this.issueDate = issueDate;
            this.dueDate = dueDate;
        }

        private String borrowerName;
        private String bookTitle;

        public IssuedBook(int issueId, String bookId, String bookTitle, String borrowerLibraryId, String borrowerName, String userType, Timestamp issueDate, Timestamp dueDate) {
            this.issueId = issueId;
            this.bookTitle = bookTitle;
            this.bookId = bookId;
            this.borrowerName = borrowerName;
            this.borrowerLibraryId = borrowerLibraryId;
            this.userType = userType;
            this.issueDate = issueDate;
            this.dueDate = dueDate;
        }

        public int getIssueId() {
            return issueId;
        }

        public void setIssueId(int issueId) {
            this.issueId = issueId;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getBorrowerLibraryId() {
            return borrowerLibraryId;
        }

        public void setBorrowerLibraryId(String borrowerLibraryId) {
            this.borrowerLibraryId = borrowerLibraryId;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public Timestamp getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(Timestamp issueDate) {
            this.issueDate = issueDate;
        }

        public Timestamp getDueDate() {
            return dueDate;
        }

        public void setDueDate(Timestamp dueDate) {
            this.dueDate = dueDate;
        }

        public String getBorrowerName() {
            return borrowerName;
        }

        public void setBorrowerName(String borrowerName) {
            this.borrowerName = borrowerName;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public void setBookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
        }
    }
}
