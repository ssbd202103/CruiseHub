import store from "../redux/store";
import axios from "./URL";

export function getAllCompanies() {
    const {token} = store.getState()

    return axios.get('company/companies', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
}