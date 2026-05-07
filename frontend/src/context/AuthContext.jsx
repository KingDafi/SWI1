import { createContext, useContext, useState, useEffect } from 'react';
import { tokenService } from '../services/tokenService';
import axiosClient from '../services/axiosClient';

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
        try {
            const response = await axiosClient.post('/auth/login', credentials);
            const userData = response.data; // This is the UserToken DTO from Java

            // Use the actual UserID as a "token" for your local session
            tokenService.setTokens(userData.userId, 'dummy-refresh');
            localStorage.setItem('activeUser', JSON.stringify(userData));
            setUser(userData);
        } catch (err) {
            throw new Error(err.response?.data || 'Neplatný e-mail nebo heslo.');
        }
    };

    const register = async (userData) => {
        try {
            const response = await axiosClient.post('/auth/register', userData);
            const newUser = response.data;
            tokenService.setTokens('mock-access-token', 'mock-refresh-token');
            localStorage.setItem('activeUser', JSON.stringify(newUser));
            setUser(newUser);
        } catch (err) {
            throw new Error(err.response?.data?.message || 'Registrace se nezdařila.');
        }
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
