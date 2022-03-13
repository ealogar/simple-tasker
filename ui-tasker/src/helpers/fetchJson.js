const BASE_PATH_BE = process.env.BASE_PATH_BE || 'http://localhost:8080';

const getJson = async (url) => {
    return await fetchJson(BASE_PATH_BE + url, 'GET');
};

const postJson = async (url, body) => {
    return await fetchJson(BASE_PATH_BE + url, 'POST', body);
};

const putJson = async (url, body) => {
    return await fetchJson(BASE_PATH_BE + url, 'PUT', body);
};

const fetchJson = async (url, method, body) => {
    let result = {};
    let requestOptions = null;
    if (method !== 'GET') {
        requestOptions = {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        };
    }
    try {
        const response = await fetch(url, requestOptions);
        const data = await response.json();
        if (response.statusCode !== 200) {
            result.error = data.message;
        }
        result.data = data;

    } catch (err) {
        result.error = "Unexpected error when fetching resource";
    }

    return result;
};

export { getJson, postJson, putJson }
