
// dynamic read environment variables at runtime using env.js file in public folder
// https://www.freecodecamp.org/news/how-to-implement-runtime-environment-variables-with-create-react-app-docker-and-nginx-7f9d42a91d70/

const BASEPATH_BE = window?._env_?.BASEPATH_BE;
console.log("BASEPATH_BE");
console.log(BASEPATH_BE);

const getJson = async (url) => {
    return await fetchJson(BASEPATH_BE + url, 'GET');
};

const postJson = async (url, body) => {
    return await fetchJson(BASEPATH_BE + url, 'POST', body);
};

const putJson = async (url, body) => {
    return await fetchJson(BASEPATH_BE + url, 'PUT', body);
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
        console.log(err);
        result.error = "Unexpected error when fetching resource";
    }

    return result;
};

export { getJson, postJson, putJson }
