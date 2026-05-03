import { useBorrow } from '../../context/BorrowContext';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function BookCard({ book }) {
    const { borrowBook, isBorrowed } = useBorrow();
    const { user } = useAuth();
    const navigate = useNavigate();

    const alreadyBorrowed = isBorrowed(book.id);

    const handleBorrow = () => {
        if (!user) {
            navigate('/login');
            return;
        }
        borrowBook(book);
    };

    return (
        <div className="flex flex-col sm:flex-row items-start sm:items-center gap-4 p-5 mb-4 bg-white border border-gray-200 rounded-xl shadow-[0_2px_8px_rgba(0,0,0,0.06)] hover:shadow-[0_4px_16px_rgba(0,0,0,0.1)] transition-shadow">
            {/* Cover Image */}
            <div className="w-20 h-28 flex-shrink-0 rounded-md bg-gray-100 overflow-hidden border border-gray-200 shadow-sm">
                {book.coverUrl ? (
                    <img
                        src={book.coverUrl}
                        alt={book.title}
                        className="w-full h-full object-cover object-center"
                        onError={(e) => { e.target.style.display = 'none'; }}
                    />
                ) : (
                    <div className="w-full h-full flex items-center justify-center text-[10px] text-gray-400 text-center leading-tight p-1">
                        Bez<br />obr.
                    </div>
                )}
            </div>

            {/* Book Info */}
            <div className="flex-1 min-w-0">
                <h3 className="text-[#121212] font-bold text-lg mb-1 truncate">{book.title}</h3>
                <p className="text-gray-500 text-sm mb-1">{book.author}</p>
                <span className="inline-block text-xs px-2 py-0.5 bg-gray-100 text-gray-600 rounded-full mb-2">
          {book.category}
        </span>
                <div className="flex items-center gap-2">
                    <span className="text-[#007bff] font-bold text-sm">{book.price} Kč</span>
                    {book.originalPrice && (
                        <span className="text-gray-400 line-through text-xs">{book.originalPrice} Kč</span>
                    )}
                </div>
            </div>

            {/* Availability + Borrow Button */}
            <div className="flex flex-col items-end gap-2 flex-shrink-0">
        <span
            className={`text-xs font-medium px-2.5 py-1 rounded-full ${
                book.available && !alreadyBorrowed
                    ? 'bg-green-50 text-green-700 border border-green-200'
                    : 'bg-red-50 text-red-600 border border-red-200'
            }`}
        >
          {alreadyBorrowed ? 'Vypůjčeno vámi' : book.available ? 'Dostupná' : 'Nedostupná'}
        </span>

                <button
                    onClick={handleBorrow}
                    disabled={!book.available || alreadyBorrowed}
                    className={`px-4 py-2 rounded-md text-sm font-medium transition-colors ${
                        !book.available || alreadyBorrowed
                            ? 'bg-gray-100 text-gray-400 cursor-not-allowed border border-gray-200'
                            : 'bg-[#007bff] hover:bg-[#0056b3] text-white cursor-pointer'
                    }`}
                >
                    {alreadyBorrowed ? 'Již vypůjčeno' : 'Půjčit knihu'}
                </button>
            </div>
        </div>
    );
}
