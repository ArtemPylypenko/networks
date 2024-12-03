import axios from "axios";

const BASE_URL = proces.env.VITE_BASE_URL;
const REST_API_URL = `${BASE_URL}/departments`;

export const listDepartments = () => {
    return axios.get(REST_API_URL);
};

export const createDepartment = (department) => {
    return axios.post(REST_API_URL, department);
};

export const getDepartmentById = (id) => {
    return axios.get(REST_API_URL + "/" + id);
};

export const updateDepartment = (id, department) => {
    return axios.put(REST_API_URL + "/" + id, department);
};

export const deleteDepartment = (id) => {
    return axios.delete(REST_API_URL + "/" + id);
};
