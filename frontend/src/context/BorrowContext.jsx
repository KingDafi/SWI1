import { createContext, useContext, useState, useEffect } from 'react';
import axiosClient from '../services/axiosClient';
import { useAuth } from './AuthContext';

const BorrowContext = createContext(null);

export function BorrowProvider({ children }) {
    const { user } = useAuth();
    const [borrowedBooks, setBorrowedBooks] = useState([]);

    const fetchBorrowings = async () => {
        if (user?.userId) {
            const res = await axiosClient.get(`/borrowings/${user.userId}`);
            setBorrowedBooks(res.data.filter(b => b.returnedAt === null));
        }
    };

    useEffect(() => { fetchBorrowings(); }, [user]);

    const borrowBook = async (bookId) => {
        if (!user) return;
        await axiosClient.post(`/books/${bookId}/borrow`, { userId: user.userId });
        await fetchBorrowings(); // Okamžitá aktualizace stavu
    };

    const returnBook = async (bookId) => {
        if (!user) return;
        await axiosClient.post(`/books/${bookId}/return`, { userId: user.userId });
        await fetchBorrowings();
    };

    const isBorrowed = (bookId) => borrowedBooks.some(b => b.book.bookId === bookId);

    return (
        <BorrowContext.Provider value={{ borrowedBooks, borrowBook, returnBook, isBorrowed }}>
            {children}
        </BorrowContext.Provider>
    );
}

export const useBorrow = () => useContext(BorrowContext);