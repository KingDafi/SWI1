import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

/**
 * Wraps routes that require authentication.
 * Shows nothing while the session is being restored to avoid flash-redirect.
 */
export default function ProtectedRoute() {
    const { user, loading } = useAuth();

    if (loading) return null;
    if (!user)   return <Navigate to="/login" replace />;

    return <Outlet />;
}