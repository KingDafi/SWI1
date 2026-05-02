import { Outlet, Link, useNavigate } from 'react-router-dom';
import { Library } from 'lucide-react';
import { useAuth } from '../../context/AuthContext';

export default function Layout() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <div className="flex flex-col min-h-screen bg-[#f8f8f8] font-sans">
            {/* Header */}
            <header className="bg-[#121212] text-white flex justify-between items-center px-5 py-2.5 flex-wrap">
                <div className="flex items-center gap-2.5">
                    <Link to="/" className="flex items-center gap-2.5 text-white decoration-transparent hover:text-white">
                        <div className="w-[50px] h-[50px] bg-white text-[#121212] flex items-center justify-center rounded-full">
                            <Library size={24} />
                        </div>
                        <span className="text-[1.5rem] font-bold leading-none">EduLend</span>
                    </Link>
                </div>

                <nav className="flex gap-5 items-center mt-3 sm:mt-0">
                    <Link to="/" className="text-white decoration-transparent hover:text-gray-300 transition-colors">Katalog</Link>
                    <Link to="/dashboard" className="text-white decoration-transparent hover:text-gray-300 transition-colors">Moje Výpůjčky</Link>

                    {user ? (
                        <div className="flex items-center gap-4 ml-4 pl-4 border-l border-gray-700">
                            <span className="text-gray-300 text-sm">Právě přihlášen: {user.name}</span>
                            <button
                                onClick={handleLogout}
                                className="text-white hover:text-red-400 text-sm decoration-transparent transition-colors"
                            >
                                Odhlásit se
                            </button>
                        </div>
                    ) : (
                        <Link to="/login" className="text-white decoration-transparent hover:text-gray-300 transition-colors">Přihlásit</Link>
                    )}
                </nav>
            </header>

            {/* Main Content Area */}
            <main className="flex-1 w-full max-w-[1000px] mx-auto px-5 py-10">
                <Outlet />
            </main>

            {/* Footer */}
            <footer className="bg-[#121212] text-white text-center p-3 mt-auto w-full">
                <p className="mb-2">&copy; 2026 EduLend. Všechna práva vyhrazena.</p>
                <Link to="/" className="text-gray-300 decoration-transparent mx-2 hover:text-white">O nás</Link> |
                <Link to="/" className="text-gray-300 decoration-transparent mx-2 hover:text-white">Kontakt</Link>
            </footer>
        </div>
    );
}