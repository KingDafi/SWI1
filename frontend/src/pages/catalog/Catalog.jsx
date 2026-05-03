import { useBooks } from '../../hooks/useBooks';
import BookCard from '../../components/books/BookCard';

export default function Catalog() {
    const { books, query, setQuery } = useBooks();

    return (
        <div>
            <div className="mb-8 border-b border-gray-200 pb-6">
                <h1 className="text-[2.2rem] font-bold text-[#121212] mb-4">Katalog knih</h1>
                <p className="text-gray-600 mb-6 text-lg">
                    Vyhledejte si odbornou literaturu, skripta nebo beletrii pro vaše studium.
                </p>
                <input
                    type="text"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="Hledat podle názvu, autora nebo kategorie…"
                    className="w-full sm:w-1/2 px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:border-[#007bff] focus:ring-1 focus:ring-[#007bff]"
                />
            </div>

            <div>
                {books.length === 0 ? (
                    <p className="text-gray-500 py-8">Žádné knihy nebyly nalezeny.</p>
                ) : (
                    books.map((book) => <BookCard key={book.id} book={book} />)
                )}
            </div>
        </div>
    );
}
