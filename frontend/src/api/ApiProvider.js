import axios from 'axios';

// GET example (getData(payload).then(res => console.log(res)))
export const getData = (somePayload) => {
    return axios.get("/api/data", somePayload).then(response => response.data)
};

// POST example
export const postData = (somePayload) => {
    return axios.post("/api/register", somePayload).then(response => response.data)
};