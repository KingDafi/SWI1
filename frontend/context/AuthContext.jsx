import { createContext, useContext, useState, useEffect } from 'react';
import { tokenService } from '../tokenService';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = tokenService.getAccess();
        if (token) {
            const activeUser = JSON.parse(localStorage.getItem('activeUser'));
            // eslint-disable-next-line react-hooks/set-state-in-effect
            if (activeUser) setUser(activeUser);
        }
        setLoading(false);
    }, []);

    const login = async (credentials) => {
        const users = JSON.parse(localStorage.getItem('library_users')) || [];
        const validUser = users.find(u => u.email === credentials.email && u.password === credentials.password);

        if (!validUser) throw new Error('Neplatný e-mail nebo heslo.');

        const userData = { name: validUser.name, role: validUser.role, email: validUser.email };

        tokenService.setTokens('mock-access-token', 'mock-refresh-token');
        localStorage.setItem('activeUser', JSON.stringify(userData));
        setUser(userData);
    };

    const register = async (userData) => {
        const users = JSON.parse(localStorage.getItem('library_users')) || [];

        if (users.some(u => u.email === userData.email)) {
            throw new Error('Uživatel s tímto e-mailem již existuje.');
        }

        const newUser = { ...userData, role: 'MEMBER' };
        users.push(newUser);
        localStorage.setItem('library_users', JSON.stringify(users));

        const sessionData = { name: newUser.name, role: newUser.role, email: newUser.email };
        tokenService.setTokens('mock-access-token', 'mock-refresh-token');
        localStorage.setItem('activeUser', JSON.stringify(sessionData));
        setUser(sessionData);
    };

    const logout = () => {
        setUser(null);
        localStorage.removeItem('activeUser');
        tokenService.clearTokens();
    };

    return (
        <AuthContext.Provider value={{ user, loading, login, register, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

// eslint-disable-next-line react-refresh/only-export-components
export const useAuth = () => useContext(AuthContext);