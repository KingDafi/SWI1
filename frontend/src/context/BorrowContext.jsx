import { createContext, useContext, useState, useEffect } from 'react';

const BorrowContext = createContext(null);

function loadFromStorage() {
    try {
        const stored = localStorage.getItem('borrowedBooks');
        return stored ? JSON.parse(stored) : [];
    } catch {
        return [];
    }
}

export function BorrowProvider({ children }) {
    const [borrowedBooks, setBorrowedBooks] = useState(loadFromStorage);

    useEffect(() => {
        localStorage.setItem('borrowedBooks', JSON.stringify(borrowedBooks));
    }, [borrowedBooks]);

    const borrowBook = (book) => {
        if (!book.available) {
            alert('Tato kniha není momentálně dostupná.');
            return false;
        }
        if (borrowedBooks.find((b) => b.id === book.id)) {
            alert('Tuto knihu už máte vypůjčenou.');
            return false;
        }

        setBorrowedBooks((prev) => [
            ...prev,
            { ...book, available: false, borrowedDate: new Date().toISOString() },
        ]);
        alert(`Kniha "${book.title}" byla úspěšně vypůjčena!`);
        return true;
    };

    const returnBook = (bookId) => {
        setBorrowedBooks((prev) => prev.filter((b) => b.id !== bookId));
    };

    const isBorrowed = (bookId) => borrowedBooks.some((b) => b.id === bookId);

    return (
        <BorrowContext.Provider value={{ borrowedBooks, borrowBook, returnBook, isBorrowed }}>
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
