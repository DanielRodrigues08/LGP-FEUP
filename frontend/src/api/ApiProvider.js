import axios from 'axios';

export const getData = (somePayload) => {
    return axios.get("/api/data", somePayload).then(response => response.data)
};

// POST example
export const postData = (somePayload) => {
    return axios.post("/api/register", somePayload).then(response => response.data)
};

export const postProcess = (process) => {
    return axios.post("/api/processes/create", process).then(response => response.data)
};