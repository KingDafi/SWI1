import { useBorrow } from '../../context/BorrowContext';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function BookCard({ book }) {
    const { borrowBook, isBorrowed } = useBorrow();
    const { user } = useAuth();
    const navigate = useNavigate();

    const alreadyBorrowed = isBorrowed(book.bookId);
    const isOutOfStock = book.availableQuantity <= 0;

    const handleBorrow = () => {
        if (!user) return navigate('/login');
        if (alreadyBorrowed || isOutOfStock) return;
        borrowBook(book.bookId);
    };

    return (
        <div className="flex flex-col sm:flex-row justify-between items-center p-5 mb-4 bg-white border border-gray-200 rounded-xl shadow-sm">

            {/* Levá část: Obrázek + Text */}
            <div className="flex w-full sm:w-auto items-center mb-4 sm:mb-0">

                {/* 1. Obálka knihy */}
                <div className="w-16 h-24 flex-shrink-0 mr-4 rounded-md bg-gray-100 overflow-hidden border border-gray-200 shadow-sm">
                    {book.coverUrl ? (
                        <img
                            src={book.coverUrl}
                            alt={book.title}
                            className="w-full h-full object-cover object-center"
                        />
                    ) : (
                        <div className="w-full h-full flex items-center justify-center text-[10px] text-gray-400 text-center leading-tight p-1">
                            Bez<br/>obr.
                        </div>
                    )}
                </div>

                {/* 2. Informace o knize */}
                <div>
                    <h3 className="font-bold text-gray-900 m-0 text-lg">{book.title}</h3>
                    <p className="text-gray-500 text-sm m-0 mt-1">{book.author}</p>



                </div>
            </div>

            {/* Pravá část: Tlačítko */}
            <button
                onClick={handleBorrow}
                disabled={alreadyBorrowed || isOutOfStock}
                className={`w-full sm:w-auto px-6 py-2 rounded-md font-medium transition-colors ${
                    alreadyBorrowed
                        ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                        : isOutOfStock
                            ? 'bg-red-50 text-red-500 border border-red-200 cursor-not-allowed'
                            : 'bg-[#007bff] text-white hover:bg-[#0056b3] cursor-pointer'
                }`}
            >
                {alreadyBorrowed ? 'Půjčeno' : (!isOutOfStock ? 'Půjčit' : 'Nedostupné')}
            </button>
        </div>
    );
}