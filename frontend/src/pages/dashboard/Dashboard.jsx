import { useBorrow } from '../../context/BorrowContext';

export default function Dashboard() {
    const { borrowedBooks, returnBook } = useBorrow();

    return (
        <div className="max-w-[900px] mx-auto font-sans leading-relaxed">
            <h1 className="text-[2.2rem] mb-5 text-[#121212] font-bold border-b border-gray-200 pb-4">
                Moje výpůjčky
            </h1>
            <p className="text-gray-700 text-lg mb-4">
                Zde naleznete přehled všech vašich aktuálně vypůjčených knih a historii minulých výpůjček.
            </p>

            <div className="mt-8 space-y-4">
                {borrowedBooks.length === 0 ? (
                    <div className="bg-white p-6 rounded-xl shadow-[0_2px_6px_rgba(0,0,0,0.05)] border border-[#eee]">
                        <p className="text-[#555]">Zatím nemáte žádné aktivní výpůjčky.</p>
                    </div>
                ) : (
                    borrowedBooks.map((book) => (
                        <div
                            key={book.id}
                            className="flex flex-col sm:flex-row justify-between items-center p-5 border border-[#eee] bg-white rounded-xl shadow-[0_2px_6px_rgba(0,0,0,0.05)]"
                        >
                            <div className="flex w-full sm:w-auto items-center mb-4 sm:mb-0">
                                {/* Thumbnail */}
                                <div className="w-16 h-24 sm:w-20 sm:h-28 flex-shrink-0 mr-4 rounded-md bg-gray-100 overflow-hidden border border-gray-200 shadow-sm">
                                    {book.coverUrl ? (
                                        <img
                                            src={book.coverUrl}
                                            alt={book.title}
                                            className="w-full h-full object-cover object-center"
                                        />
                                    ) : (
                                        <div className="w-full h-full flex items-center justify-center text-[10px] text-gray-400 text-center leading-tight p-1">
                                            Bez<br />obr.
                                        </div>
                                    )}
                                </div>

                                {/* Details */}
                                <div>
                                    <h3 className="m-0 mb-1 text-[#222] text-xl font-bold">{book.title}</h3>
                                    <p className="text-[#555] text-sm m-0">Autor: {book.author}</p>
                                    <p className="text-[#555] text-sm m-0 mt-1">
                                        {/* ✅ FIX: borrowedDate is ISO string from localStorage, not Date object */}
                                        Vypůjčeno: {new Date(book.borrowedDate).toLocaleDateString('cs-CZ')}
                                    </p>
                                </div>
                            </div>

                            {/* Return Button */}
                            <button
                                onClick={() => returnBook(book.id)}
                                className="w-full sm:w-auto px-4 py-2 bg-red-50 text-red-600 hover:bg-red-100 border border-red-200 rounded-md font-medium transition-colors"
                            >
                                Vrátit knihu
                            </button>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}
