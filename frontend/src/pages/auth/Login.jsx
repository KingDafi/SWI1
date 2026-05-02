import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

export default function Login() {
    const { login } = useAuth();
    const navigate = useNavigate();
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const [formData, setFormData] = useState({ email: '', password: '' });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleLogin = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);
        try {
            await login(formData);
            navigate('/');
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex items-center justify-center mt-10">
            <div className="bg-white p-8 rounded-xl shadow-[0_4px_10px_rgba(0,0,0,0.05)] border border-gray-200 w-full max-w-md">
                <h2 className="text-[1.5rem] font-bold mb-4 text-[#121212]">Přihlášení do knihovny</h2>

                {error && <p className="text-red-600 text-sm mb-4">{error}</p>}

                <form onSubmit={handleLogin}>
                    <div className="mb-4">
                        <label className="block text-gray-700 mb-2 font-medium">Email</label>
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-[#007bff]" // ✅ FIX: #007b| → #007bff
                            placeholder="Zadejte svůj e-mail"
                            required
                        />
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-700 mb-2 font-medium">Heslo</label>
                        <input
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-[#007bff]" // ✅ FIX
                            placeholder="Zadejte heslo"
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-[#007bff] hover:bg-[#0056b3] text-white font-medium py-2.5 rounded-md transition-colors" // ✅ FIX
                    >
                        {loading ? 'Přihlašuji…' : 'Přihlásit se'}
                    </button>
                </form>

                <div className="text-center text-sm text-gray-600 border-t border-gray-100 pt-4 mt-6">
                    Nemáte ještě účet?{' '}
                    <Link to="/register" className="text-[#007bff] hover:underline font-medium"> {/* ✅ FIX */}
                        Zaregistrujte se
                    </Link>
                </div>
            </div>
        </div>
    );
}
