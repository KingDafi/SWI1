const KEYS = { access: 'accessToken', refresh: 'refreshToken' };

export const tokenService = {
    getAccess: () => localStorage.getItem(KEYS.access),
    getRefresh: () => localStorage.getItem(KEYS.refresh),
    setTokens: (a, r) => {
        localStorage.setItem(KEYS.access, a);
        localStorage.setItem(KEYS.refresh, r);
    },
    clearTokens: () => {
        localStorage.removeItem(KEYS.access);
        localStorage.removeItem(KEYS.refresh);
    },
};
