package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.repository.BookRepository;

public class BookService {
    BookRepository bookRepository = new BookRepository();

    public String booksIssuedToday() {
        if(bookRepository.booksIssuedToday() == 0) {
            return "0";
        }
        return Integer.toString(bookRepository.booksIssuedToday());
    }

    public String booksDueToday() {
        int result = bookRepository.booksDueTodayForStudent() + bookRepository.booksDueTodayForStaff();

        if(result == 0) {
            return "0";
        }
        return Integer.toString(result);
    }

    public String availableBooks() {
        if(bookRepository.availableBooks() == 0) {
            return "0";
        }
        return Integer.toString(bookRepository.availableBooks());
    }
}
