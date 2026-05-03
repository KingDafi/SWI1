import { useState, useMemo } from 'react';

const MOCK_BOOKS = [
    {
        id: 1,
        title: 'English Vocabulary in Use: Pre-intermediate',
        author: 'Stuart Redman',
        category: 'Učebnice',
        available: true,
        price: 550,
        originalPrice: 650,
        coverUrl: '/covers/vocabulary.jpg',
    },
    {
        id: 2,
        title: 'Duna',
        author: 'Frank Herbert',
        category: 'Sci-Fi',
        available: false,
        price: 350,
        originalPrice: null,
        coverUrl: '/covers/duna.png',
    },
    {
        id: 3,
        title: 'Clean Code: A Handbook of Agile Software Craftsmanship',
        author: 'Robert C. Martin',
        category: 'Programování',
        available: true,
        price: 890,
        originalPrice: 1100,
        coverUrl: '/covers/cleancode.jpg',
    },
    {
        id: 4,
        title: '1984',
        author: 'George Orwell',
        category: 'Klasická beletrie',
        available: true,
        price: 250,
        originalPrice: 320,
        coverUrl: '/covers/1984.jpg',
    },
    {
        id: 5,
        title: 'Sapiens: Úchvatný příběh lidstva',
        author: 'Yuval Noah Harari',
        category: 'Historie / Populárně naučná',
        available: true,
        price: 450,
        originalPrice: 550,
        coverUrl: '/covers/sapiens.jpg',
    },
    {
        id: 6,
        title: 'Pán Prstenů: Společenstvo Prstenu',
        author: 'J.R.R. Tolkien',
        category: 'Fantasy',
        available: false,
        price: 390,
        originalPrice: null,
        coverUrl: '/covers/lotr.jpg',
    },
    {
        id: 7,
        title: 'Atomic Habits',
        author: 'James Clear',
        category: 'Productivity',
        available: true,
        price: 430,
        originalPrice: 550,
        coverUrl: '/covers/atomic.jpg',
    },
];

export function useBooks() {
    const [query, setQuery] = useState('');

    const filtered = useMemo(
        () =>
            MOCK_BOOKS.filter(
                (b) =>
                    b.title.toLowerCase().includes(query.toLowerCase()) ||
                    b.author.toLowerCase().includes(query.toLowerCase()) ||
                    b.category.toLowerCase().includes(query.toLowerCase())
            ),
        [query]
    );

    return { books: filtered, query, setQuery };
}
