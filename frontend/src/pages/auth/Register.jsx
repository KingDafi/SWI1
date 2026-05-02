import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

export default function Register() {
    const { register } = useAuth();
    const navigate = useNavigate();
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const [formData, setFormData] = useState({ name: '', email: '', password: '' });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);
        try {
            await register(formData);
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
                <h2 className="text-[1.5rem] font-bold mb-4 text-[#121212]">Vytvořit účet</h2>

                {error && <p className="text-red-600 text-sm mb-4">{error}</p>}

                <form onSubmit={handleRegister}>
                    <div className="mb-4">
                        <label className="block text-gray-700 mb-2 font-medium">Jméno a příjmení</label>
                        <input
                            type="text"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-[#007bff]"
                            placeholder="Jan Novák"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700 mb-2 font-medium">Email</label>
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-[#007bff]"
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
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:border-[#007bff]"
                            placeholder="Zvolte heslo"
                            minLength={6}
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-[#007bff] hover:bg-[#0056b3] text-white font-medium py-2.5 rounded-md transition-colors mb-4"
                    >
                        {loading ? 'Vytvářím účet...' : 'Registrovat se'}
                    </button>
                </form>

                <div className="text-center text-sm text-gray-600 border-t border-gray-100 pt-4 mt-2">
                    Již máte účet?{' '}
                    <Link to="/login" className="text-[#007bff] hover:underline font-medium">
                        Přihlaste se zde
                    </Link>
                </div>
            </div>
        </div>
    );
}
