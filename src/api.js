const API_ROOT = "http://localhost:8080";

const apiUrl = (path) => {
    return API_ROOT + path;
};

export const api = (method, path, body = null, token = null) => {
    const headers = {
        "Content-Type": "application/json"
    };

    if (token) {
        headers["Authorization"] = `Bearer ${token}`;
    }

    const bodyJson = body ? JSON.stringify(body) : null;

    return fetch(apiUrl(path), { method, headers, body: bodyJson });
};
