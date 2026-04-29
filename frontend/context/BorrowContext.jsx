import { createContext, useContext, useState } from 'react';

const BorrowContext = createContext(null);

export function BorrowProvider({ children }) {
    const [borrowedBooks, setBorrowedBooks] = useState([]);

    const borrowBook = (book) => {
        if (!borrowedBooks.find((b) => b.id === book.id)) {
            setBorrowedBooks([...borrowedBooks, { ...book, borrowedDate: new Date() }]);
            alert(`Kniha "${book.title}" byla úspěšně zarezervována!`);
        } else {
            alert('Tuto knihu už máte vypůjčenou.');
        }
    };

    const returnBook = (bookId) => {
        setBorrowedBooks(borrowedBooks.filter((b) => b.id !== bookId));
    };

    return (
        <BorrowContext.Provider value={{ borrowedBooks, borrowBook, returnBook }}>
            {children}
        </BorrowContext.Provider>
    );
}


// eslint-disable-next-line react-refresh/only-export-components
export function useBorrow() {
    const ctx = useContext(BorrowContext);
    if (!ctx) throw new Error('useBorrow must be used within a BorrowProvider');
    return ctx;
}