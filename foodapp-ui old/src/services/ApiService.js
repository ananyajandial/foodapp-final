import axios from "axios";

const API_URL = "http://localhost:9090";

export const loginUser = async (
    username,
    password
) => {

    const basicToken = btoa(username + ":" + password);

    console.log("ApiService is Calling: http://localhost:9090/me")

    return axios.get(
        `${API_URL}/me`,
        {
            headers: {
                Authorization:
                    `Basic ${basicToken}`
            }
        }
    );
};

export const apiRequest = async (
    method,
    endpoint,
    token,
    data = null
) => {

    return axios({
        method: method,
        url: `${API_URL}${endpoint}`,
        data: data,
        headers: {
            Authorization:
                `Basic ${token}`,
            "Content-Type":
                "application/json"
        }
    });

};
