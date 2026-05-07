import { useState, useEffect, useMemo } from 'react';
import axiosClient from '../services/axiosClient';

export function useBooks() {
    const [allBooks, setAllBooks] = useState([]);
    const [query, setQuery] = useState('');

    useEffect(() => {
        // This calls your @GetMapping in BookController
        const url = query ? `/books?query=${encodeURIComponent(query)}` : '/books';

        axiosClient.get(url)
            .then(res => setAllBooks(res.data))
            .catch(err => console.error('Failed to fetch books', err));
    }, [query]); // This triggers a new DB search every time the user types

    const books = useMemo(() =>
        allBooks.filter(b =>
            (b.title?.toLowerCase() ?? '').includes(query.toLowerCase()) ||
            (b.author?.toLowerCase() ?? '').includes(query.toLowerCase()) ||
            (b.category?.toLowerCase() ?? '').includes(query.toLowerCase())
        ), [allBooks, query]);

    return { books, query, setQuery };
}