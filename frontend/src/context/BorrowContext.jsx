import { createContext, useContext } from 'react';
import axiosClient from '../services/axiosClient';

const BorrowContext = createContext(null);

export function BorrowProvider({ children }) {

    const borrowBook = async (bookId, userId) => {
        await axiosClient.post(`/books/${bookId}/borrow`, {
            userId,
        });
    };

    const handleBorrow = () => {
        if (!user) {
            navigate('/login');
            return;
        }
        borrowBook(book.bookId, user.userId);
    };

    const returnBook = async (bookId, userId) => {
        await axiosClient.post(`/books/${bookId}/return`, { userId });
    };

    return (
        <BorrowContext.Provider value={{ borrowBook, returnBook }}>
            {children}
        </BorrowContext.Provider>
    );
}

export function useBorrow() {
    const ctx = useContext(BorrowContext);
    if (!ctx) throw new Error('useBorrow must be used within a BorrowProvider');
    return ctx;
}
