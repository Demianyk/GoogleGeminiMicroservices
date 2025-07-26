const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || "";

const getCsrfTokenFromCookie = (name = 'XSRF-TOKEN'): string => {
    const value = `; ${document.cookie}`;
    console.log(value);
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
        return parts.pop().split(';').shift();
    }
    return null;
}

export const postApi = async <T>(url: string, data: any): Promise<T> => {
    return initIfRequired(() => doPost<T>(url, data));
};

export const deleteApi = async (url: string): Promise<any> => {
    return initIfRequired(() => doDelete(url));
};

const initIfRequired = <T>(callBack: () => Promise<T>) => {
    let csrfToken = getCsrfTokenFromCookie();
    return csrfToken ? callBack() : init().then(callBack, callBack);
}

/**
 * Send a POST request to existing endpoint. This call will fail with 403 because X-XSRF-TOKEN header is not set.
 * This is how CSRF protection works in Spring Boot.
 * However, backend will set the XSRF-TOKEN cookie in the response, so the following requests will be able to set the X-XSRF-TOKEN
 * by value of the cookie.
 */

const init = () => {
    return fetch(API_BASE_URL + '/api/init', {
        credentials: "include",
        method: 'POST',
    })
}

const doPost = async <T>(url: string, data: any): Promise<T> => {
    return fetch(API_BASE_URL + url,
        {
            method: 'POST',
            body: JSON.stringify(data),
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                'X-XSRF-TOKEN': decodeURIComponent(getCsrfTokenFromCookie()),
            },
        })
        .then(response => {
            return response.json();
        })
}

const doDelete = async (url: string): Promise<any> => {
    return fetch(API_BASE_URL + url,
        {
            method: 'DELETE',
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                'X-XSRF-TOKEN': decodeURIComponent(getCsrfTokenFromCookie()),
            },
        })
}